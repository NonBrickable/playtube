package com.bilibili.service;

import com.bilibili.pojo.UserMoments;

import java.util.List;

public interface UserMomentsService {

    /**
     * 新增动态
     * @param userMoments
     */
    void addUserMoments(UserMoments userMoments) throws Exception;

    /**
     * 获取动态
     * @param userId 用户id
     * @param start 开始条数
     * @param end   结束条数
     * @return
     */
    List<UserMoments> getUserSubscribedMoments(Long userId, Long start, Long end);
}
