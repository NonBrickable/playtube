package com.playtube.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.playtube.common.constant.UserMomentsConstant;
import com.playtube.dao.UserMomentsDao;
import com.playtube.pojo.UserMoments;
import com.playtube.service.UserMomentsService;
import com.playtube.util.RocketMQUtil;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMomentsServiceImpl implements UserMomentsService {
    private final UserMomentsDao userMomentsDao;
    @Autowired
    private static ApplicationContext applicationContext;
    private final RedisTemplate<String,String> redisTemplate;

    public void addUserMoments(UserMoments userMoments) throws Exception {
        userMomentsDao.addUserMoments(userMoments);
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentProducer");
        Message msg = new Message(UserMomentsConstant.MOMENTS_TOPIC, JSONObject.toJSONString(userMoments).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSendMsg(producer,msg);
    }
    public List<UserMoments> getUserSubscribedMoments(Long userId,Long start,Long end) {
        String key ="subscribed-" + userId;
        List<String> list = redisTemplate.opsForList().range(key,start,end);
        List<UserMoments> result = new ArrayList<>();
        for(String s : list){
            result.add(JSONObject.parseObject(s,UserMoments.class));
        }
        return result;
    }
}
