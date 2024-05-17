package com.playtube.service.impl;

import com.playtube.common.constant.UserConstant;
import com.playtube.dao.FollowingGroupDao;
import com.playtube.common.exception.ConditionException;
import com.playtube.pojo.FollowingGroup;
import com.playtube.service.FollowingGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowingGroupServiceImpl implements FollowingGroupService {
    private final FollowingGroupDao followingGroupDao;

    public FollowingGroup getByType(String type){
        return followingGroupDao.getByType(type);
    }
    public FollowingGroup getById(Long id){
        return followingGroupDao.getById(id);
    }

    public List<FollowingGroup> getFollowingGroupByUserId(Long userId){
        return followingGroupDao.getFollowingGroupByUserId(userId);
    }
    public Long addUserFollowingGroup(FollowingGroup followingGroup){
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupDao.addUserFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    public void deleteUserFollowingGroup(FollowingGroup followingGroup) {
        Long id = followingGroup.getId();
        if(id == null){
            throw new ConditionException("分组不存在");
        }
        followingGroupDao.deleteUserFollowingGroup(followingGroup);
    }
}













