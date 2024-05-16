package com.bilibili.service;

import com.bilibili.pojo.Barrage;

import java.util.List;

public interface BarrageService {

    /**
     * 获取弹幕/条件筛选弹幕
     * @param videoId   视频id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws Exception
     */
    List<Barrage> getBarrages(Long videoId, String startTime, String endTime) throws Exception;

    /**
     * 放一条数据到Redis中
     * @param barrage
     */
    void addBarrageToRedis(Barrage barrage);
}
