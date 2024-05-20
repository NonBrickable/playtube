package com.playtube.service;

import com.playtube.pojo.Barrage;

import java.util.List;

public interface BarrageService {

    /**
     * 获取弹幕/条件筛选弹幕
     * @param videoId   视频id
     * @return
     * @throws Exception
     */
    List<Barrage> getBarrages(Long videoId);

    /**
     * 放一条数据到Redis中
     * @param barrage
     */
    void addBarrageToRedis(Barrage barrage);

    /**
     * 异步保存到数据库
     * @param barrage
     */
    void asyncAddBarrage(Barrage barrage);
}
