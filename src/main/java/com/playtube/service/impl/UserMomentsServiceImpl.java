package com.playtube.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.playtube.common.UserContext;
import com.playtube.common.constant.RedisCacheConstant;
import com.playtube.common.constant.UserMomentsConstant;
import com.playtube.common.exception.ConditionException;
import com.playtube.dao.UserMomentsDao;
import com.playtube.pojo.UserFollowing;
import com.playtube.pojo.UserMoments;
import com.playtube.service.UserFollowingService;
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
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserMomentsServiceImpl implements UserMomentsService {
    private final UserMomentsDao userMomentsDao;
    private final UserFollowingService userFollowingService;
    @Autowired
    private static ApplicationContext applicationContext;
    private final RedisTemplate<String,String> redisTemplate;

    public void addUserMoments(UserMoments userMoments) throws Exception {
        Long userId = UserContext.getUserId();
        userMoments.setUserId(userId);
        userMomentsDao.addUserMoments(userMoments);
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentProducer");
        Message msg = new Message(UserMomentsConstant.MOMENTS_TOPIC, JSONObject.toJSONString(userMoments).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSendMsg(producer,msg);
    }

    public List<UserMoments> getUserSubscribedMoments(Long start,Long end) {
        if(start < 0L || start >= end){
            throw new ConditionException("参数错误");
        }
        Long userId = UserContext.getUserId();
        String key = String.format(RedisCacheConstant.MOMENTS_CACHE,userId);
        //从Redis缓存中获取数据
        List<String> list = redisTemplate.opsForList().range(key,start,end);
        List<UserMoments> result = new ArrayList<>();
        if(!CollectionUtil.isEmpty(list)){
            for(String s : list){
                result.add(JSONObject.parseObject(s,UserMoments.class));
            }
            redisTemplate.expire(key,1L,TimeUnit.DAYS);
        }
        return result;
    }

    public List<UserMoments> getUserSubscribedMomentsActive(Long start,Long end){
        Long userId = UserContext.getUserId();
        List<UserFollowing> followingUserList = userFollowingService.getUserFollowings(userId);
        List<Long> followingUserIdList = followingUserList.stream().map(UserFollowing::getFollowingId).toList();
        List<UserMoments> userMomentsList = userMomentsDao.getUserMomentsByUserId(followingUserIdList);
        if(!CollectionUtil.isEmpty(userMomentsList)){
            start = start < userMomentsList.size() ? start : -1;
            end = end <= userMomentsList.size() ? end : -1;
            if(start == -1 || end == -1){
                return new ArrayList<>();
            }
            return userMomentsList.subList(Integer.parseInt(String.valueOf(start)),Integer.parseInt(String.valueOf(end)));
        }
        return new ArrayList<>();
    }
}
