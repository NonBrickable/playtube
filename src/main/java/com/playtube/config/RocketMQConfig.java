package com.playtube.config;

import com.alibaba.fastjson.JSONObject;
import com.playtube.common.constant.RedisCacheConstant;
import com.playtube.common.constant.UserMomentsConstant;
import com.playtube.controller.websocket.WebSocketService;
import com.playtube.pojo.UserFollowing;
import com.playtube.pojo.UserMoments;
import com.playtube.service.UserFollowingService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

//mq的配置类
@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.nameServer}")
    private String nameServerAddr;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserFollowingService userFollowingService;

    @Bean("momentProducer")
    public DefaultMQProducer momentProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.MOMENTS_GROUP);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    @Bean("momentConsumer")
    public DefaultMQPushConsumer momentConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.MOMENTS_GROUP);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.MOMENTS_TOPIC, "*");//订阅主题与二级主题
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            MessageExt msg = list.get(0);
            if (msg == null) {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            String bodyStr = new String(msg.getBody());
            UserMoments userMoments = JSONObject.parseObject(bodyStr,UserMoments.class);
            Long userId = userMoments.getUserId();
            List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
            for (UserFollowing fan : userFans) {
                String key = String.format(RedisCacheConstant.MOMENTS_CACHE,fan.getUserId().toString());
                redisTemplate.opsForList().leftPush(key,bodyStr);
                if(redisTemplate.getExpire(key) == -1L){
                    redisTemplate.expire(key,1, TimeUnit.DAYS);
                }
                while(redisTemplate.opsForList().size(key) > 200){
                    redisTemplate.opsForList().rightPop(key);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }

    @Bean("barrageProducer")
    public DefaultMQProducer barrageProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.BARRAGE_GROUP);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    @Bean("barrageConsumer")
    public DefaultMQPushConsumer barrageConsumer() throws Exception {
        //实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.BARRAGE_GROUP);
        //设置名称服务器地址
        consumer.setNamesrvAddr(nameServerAddr);
        //订阅一个或多个Topic，以及Tag来过滤需要消费的信息
        consumer.subscribe(UserMomentsConstant.BARRAGE_TOPIC, "*");//订阅主题与二级主题
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            MessageExt msg = list.get(0);
            if (msg == null) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            String bodyStr = new String(msg.getBody());
            JSONObject jsonObject = JSONObject.parseObject(bodyStr);
            Long videoId = Long.valueOf((String) jsonObject.get("videoId"));
            String sessionId = (String) jsonObject.get("sessionId");
            String message = (String) jsonObject.get("message");
            WebSocketService webSocketService = WebSocketService.WEBSOCKET_MAP.get(videoId).get(sessionId);
            if (webSocketService.getSession().isOpen()) {
                try {
                    webSocketService.sendMessage(message);
                } catch (Exception e) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }
}
