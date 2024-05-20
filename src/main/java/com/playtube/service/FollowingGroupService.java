package com.playtube.service;

import com.playtube.pojo.FollowingGroup;

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
     * @return
     */
    List<FollowingGroup> getFollowingGroupByUserId();

    /**
     * 获取关注分组（通过分组类型）
     * @param userFollowingGroupTypeDefault
     * @return
     */
    FollowingGroup getByType(String userFollowingGroupTypeDefault);

    /**
     * 获取关注分组(通过分组id)
     * @param groupId
     * @return
     */
    FollowingGroup getById(Long groupId);
}
