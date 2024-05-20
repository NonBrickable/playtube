package com.playtube.controller.websocket;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.playtube.common.constant.UserMomentsConstant;
import com.playtube.pojo.Barrage;
import com.playtube.service.BarrageService;
import com.playtube.util.RocketMQUtil;
import com.playtube.util.TokenUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/websocket/{videoId}/{token}")
@Slf4j
public class WebSocketService {

    /**
     * 1.当前连接数
     */
    public static final ConcurrentHashMap<Long,AtomicInteger> VIDEO_ONLINE_COUNT = new ConcurrentHashMap<>();

    /**
     * 2.存储每个客户端连接的连接信息
     */
    public static final ConcurrentHashMap<Long, ConcurrentHashMap<String,WebSocketService>> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    /**
     * 3.每个连接的session
     */
    private Session session;

    /**
     * 4.每个连接的唯一标识符
     */
    private String sessionId;

    /**
     * 5.连接者的userId
     */
    private Long userId;

    /**
     * 6.视频的id
     */
    private Long videoId;
    /**
     * 公用的上下文
     */
    private static ApplicationContext APPLICATION_CONTEXT;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketService.APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 打开连接
     * session:后端存储的session
     */
    @OnOpen
    public void openConnection(Session session, @PathParam("token") String token,@PathParam("videoId") Long videoId) {
        log.info("打开连接");
        System.out.println("打开连接");
        try {
            this.userId = TokenUtil.verifyToken(token);
        } catch (Exception e) {
        }
        this.sessionId = session.getId();
        this.session = session;
        this.videoId = videoId;
        if(WEBSOCKET_MAP.containsKey(videoId)){
            ConcurrentHashMap<String,WebSocketService> map = WEBSOCKET_MAP.get(videoId);
            if(!map.containsKey(sessionId)){
                VIDEO_ONLINE_COUNT.get(videoId).getAndIncrement();
            }
            map.put(sessionId,this);
        }else{
            ConcurrentHashMap<String,WebSocketService> map = new ConcurrentHashMap<>();
            map.put(sessionId,this);
            WEBSOCKET_MAP.put(videoId,map);
            VIDEO_ONLINE_COUNT.put(videoId,new AtomicInteger(1));
        }
        log.info("用户连接成功" + sessionId + "当前在线人数" + VIDEO_ONLINE_COUNT.get(videoId).get());
        //告诉前端连接成功
        try {
            this.sendMessage("连接成功");
        } catch (Exception e) {
            log.error("连接异常");
        }
    }

    /**
     * 关闭连接（比如服务端断了，或者客户端关闭了页面）之后调用@OnClose对应的方法
     */
    @OnClose
    public void closeConnection() {
        if(WEBSOCKET_MAP.containsKey(videoId)){
            ConcurrentHashMap<String,WebSocketService> map = WEBSOCKET_MAP.get(videoId);
            if(map.containsKey(sessionId)){
                map.remove(sessionId);
                if(VIDEO_ONLINE_COUNT.containsKey(videoId)){
                    int count = VIDEO_ONLINE_COUNT.get(videoId).decrementAndGet();
                    if(count <= 0){
                        VIDEO_ONLINE_COUNT.remove(videoId);
                    }
                }
            }
        }
        log.info("用户退出" + sessionId + "当前在线人数" + (VIDEO_ONLINE_COUNT.containsKey(videoId) ? VIDEO_ONLINE_COUNT.get(videoId).get() : 0));
    }

    /**
     * 有消息通讯时调用
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("视频id" + videoId + "用户信息：" + sessionId + ",报文：" + message);
        if (!StrUtil.isEmpty(message)) {
            try {
                //1.给连接用户群发消息
                for (Map.Entry<String, WebSocketService> entry : WEBSOCKET_MAP.get(videoId).entrySet()) {
                    WebSocketService webSocketService = entry.getValue();
                    //获取生产者
                    DefaultMQProducer defaultMQProducer = (DefaultMQProducer) APPLICATION_CONTEXT.getBean("barrageProducer");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", message);
                    jsonObject.put("videoId", videoId.toString());
                    jsonObject.put("sessionId", webSocketService.getSessionId());
                    Message msg = new Message(UserMomentsConstant.BARRAGE_TOPIC,jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
                    RocketMQUtil.asyncSendMsg(defaultMQProducer, msg);
                }
                //2.如果不是访客，则保存到数据库
                if (userId != null) {
                    Barrage barrage = JSONObject.parseObject(message, Barrage.class);
                    barrage.setUserId(userId);
                    BarrageService barrageService = (BarrageService) APPLICATION_CONTEXT.getBean("barrageService");
                    //异步保存到mysql数据库中
                    barrageService.asyncAddBarrage(barrage);
                    //同步保存到Redis中
                    barrageService.addBarrageToRedis(barrage);
                }
            } catch (Exception e) {
                log.error("弹幕接收出现问题");
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.error("出现error：" + error);
    }

    /**
     * 给前端发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    //定时向前端推送在线人数
    @Scheduled(fixedRate = 5000)
    public void noticeOnlineCount() throws IOException {
        if(videoId == null || ObjectUtil.isEmpty(WEBSOCKET_MAP.get(videoId))) return;
        for (Map.Entry<String, WebSocketService> entry : WebSocketService.WEBSOCKET_MAP.get(videoId).entrySet()) {
            WebSocketService webSocketService = entry.getValue();
            if (webSocketService.getSession().isOpen()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("onlineCount",VIDEO_ONLINE_COUNT.get(videoId).get());
                webSocketService.sendMessage(jsonObject.toJSONString());
            }
        }
    }

    public Session getSession() {
        return session;
    }

    public String getSessionId() {
        return sessionId;
    }
}
