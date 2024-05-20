package com.playtube.service;

import com.playtube.pojo.FollowingGroup;
import com.playtube.pojo.UserFollowing;
import com.playtube.pojo.UserInfo;

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
     * @return
     */
    List<FollowingGroup> getUserFollowings();


    /**
     * 获取粉丝列表
     * @return
     */
    List<UserFollowing> getUserFans();
}
