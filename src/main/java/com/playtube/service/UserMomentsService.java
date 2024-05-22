package com.playtube.service;

import com.playtube.pojo.UserMoments;

import java.util.List;

public interface UserMomentsService {

    /**
     * 新增动态
     * @param userMoments
     */
    void addUserMoments(UserMoments userMoments) throws Exception;

    /**
     * 轮询获取1天内发布的动态
     * @return
     */
    List<UserMoments> getUserSubscribedMoments();

    /**
     * 用户主动获取动态
     * @param size 每页多少条
     * @param no 当前页数
     * @return
     */
    List<UserMoments> getUserSubscribedMomentsActive(Long size,Long no);
}
