package com.playtube.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.playtube.common.constant.RedisCacheConstant;
import com.playtube.dao.BarrageDao;
import com.playtube.pojo.Barrage;
import com.playtube.service.BarrageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BarrageServiceImpl implements BarrageService {
    private final BarrageDao barrageDao;
    private final RedisTemplate<String, String> redisTemplate;

    @Async("asyncPoolTaskExecutor")
    public void asyncAddBarrage(Barrage barrage) {
        barrageDao.addBarrage(barrage);
    }

    public List<Barrage> getBarrages(Long videoId) {
        String key = RedisCacheConstant.VIDEO_BARRAGE + videoId;
        List<String> barrageJSONList = redisTemplate.opsForList().range(key,0,-1);
        if(!CollectionUtil.isEmpty(barrageJSONList)){
            return JSONArray.parseArray(barrageJSONList.toString(),Barrage.class);
        }else{
            List<Barrage> list = barrageDao.getBarrages(videoId);
            if(!CollectionUtil.isEmpty(list)){
                for(Barrage barrage : list){
                    redisTemplate.opsForList().rightPush(key,JSONObject.toJSONString(barrage));
                }
                redisTemplate.expire(key,1,TimeUnit.DAYS);
            }
            return list;
        }
    }

    public void addBarrageToRedis(Barrage barrage) {
        String key = RedisCacheConstant.VIDEO_BARRAGE + barrage.getVideoId();
        //获取redis里关于某视频的所有弹幕
        redisTemplate.opsForList().rightPush(key, JSONObject.toJSONString(barrage));
        if (redisTemplate.getExpire(key) == -1) {
            redisTemplate.expire(key, 1, TimeUnit.DAYS);
        }
    }
}
