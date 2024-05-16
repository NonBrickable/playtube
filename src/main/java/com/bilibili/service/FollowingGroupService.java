package com.bilibili.service;

import com.bilibili.pojo.FollowingGroup;

import java.util.List;

public interface FollowingGroupService {

    /**
     * 添加用户分组
     * @param followingGroup
     * @return
     */
    Long addUserFollowingGroup(FollowingGroup followingGroup);

    /**
     * 删除用户分组
     * @param followingGroup
     */
    void deleteUserFollowingGroup(FollowingGroup followingGroup);

    /**
     * 获取用户关注列表
     * @param userId
     * @return
     */
    List<FollowingGroup> getFollowingGroupByUserId(Long userId);
}
