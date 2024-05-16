package com.bilibili.service;

import com.bilibili.pojo.FollowingGroup;
import com.bilibili.pojo.UserFollowing;
import com.bilibili.pojo.UserInfo;

import java.util.List;

public interface UserFollowingService {

    /**
     * 查询关注用户信息
     * @param list
     * @param userId
     * @return
     */
    List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);

    /**
     * 新增关注用户
     * @param userFollowing
     */
    void addUserFollowings(UserFollowing userFollowing);

    /**
     * 获取关注用户信息
     * @param userId
     * @return
     */
    List<FollowingGroup> getUserFollowings(Long userId);


    /**
     * 获取粉丝列表
     * @param userId
     * @return
     */
    List<UserFollowing> getUserFans(Long userId);
}
