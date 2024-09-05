package com.playtube.service.impl;

import com.playtube.common.UserContext;
import com.playtube.common.constant.UserConstant;
import com.playtube.common.exception.ConditionException;
import com.playtube.dao.UserFollowingDao;
import com.playtube.pojo.FollowingGroup;
import com.playtube.pojo.User;
import com.playtube.pojo.UserFollowing;
import com.playtube.pojo.UserInfo;
import com.playtube.service.FollowingGroupService;
import com.playtube.service.UserFollowingService;
import com.playtube.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFollowingServiceImpl implements UserFollowingService {

    private final UserFollowingDao userFollowingDao;
    private final UserService userService;
    private final FollowingGroupService followingGroupService;

    public void addUserFollowings(UserFollowing userFollowing) {
        Long userId = UserContext.getUserId();
        userFollowing.setUserId(userId);
        Long groupId = userFollowing.getGroupId();
        if (groupId == null) {
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        } else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if (followingGroup == null) {
                throw new ConditionException("分组不存在，请创建分组");
            }
        }
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getUserById(followingId);
        if (user == null) {
            throw new ConditionException("关注用户不存在");
        }
        userFollowingDao.deleteFollowings(userId, followingId);
        userFollowingDao.addFollowings(userFollowing);
    }

    //1.获取某个用户关注的人员列表
    //2.从列表中把userId全都抽取出来
    //3.根据userId在UserInfo里面查信息（头像，昵称等），得到UserInfoList
    //4.把userFollowing对应的UserInfo存储在userFollowing中---转存1
    //5.按照userFollowing对应的分组把UserInfo放到不同分组的List里面---转存2

    //获取关注列表
    public List<FollowingGroup> getUserFollowings() {
        Long userId = UserContext.getUserId();
        //获取关注的所有人
        List<UserFollowing> list = this.getUserFollowings(userId);
        List<Long> followingIdList = new ArrayList<>();
        //抽出关注人的userId
        for (UserFollowing userFollowing : list) {
            followingIdList.add(userFollowing.getFollowingId());
        }
        //查询关注人的个人信息
        List<UserInfo> userInfoList = new ArrayList<>();
        if (followingIdList.size() > 0) {
            userInfoList = userService.getUserInfoByIds(followingIdList);
        }

        //个人信息放到UserFollowing里，这样每个userFollowing里面都有个人信息了
        for (UserFollowing userFollowing : list) {
            for (UserInfo userInfo : userInfoList) {
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        List<FollowingGroup> followingGroupList = followingGroupService.getFollowingGroupByUserId();
        //全体分组
        FollowingGroup AllFollowingGroup = new FollowingGroup();
        AllFollowingGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        AllFollowingGroup.setUserInfoList(userInfoList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(AllFollowingGroup);
        //每个分组
        for (FollowingGroup followingGroup : followingGroupList) {
            List<UserInfo> userInfos = new ArrayList<>();
            for (UserFollowing userFollowing : list) {
                if (followingGroup.getId().equals(userFollowing.getGroupId())) {
                    userInfos.add(userFollowing.getUserInfo());
                }
            }
            followingGroup.setUserInfoList(userInfos);
            result.add(followingGroup);
        }
        return result;
    }

    public List<UserFollowing> getUserFollowings(Long userId){
        return userFollowingDao.getUserFollowings(userId);
    }

    //根据userId获取UserFollowing的list
    //抽取userId
    //根据userId的list获取UserInfo的list
    //如果UserFollowing的userId和UserInfo的userId相同，就把UserInfo加入到UserFollowing中
    //获取粉丝列表,查询当前用户是否已经关注该粉丝
    public List<UserFollowing> getUserFans(Long userId) {
        userId = Optional.ofNullable(userId).orElse(UserContext.getUserId());
        //获取粉丝的粗略列表
        List<UserFollowing> fansList = userFollowingDao.getUserFansList(userId);
        List<Long> userIdList = new ArrayList<>();
        for (UserFollowing userFollowing : fansList) {
            userIdList.add(userFollowing.getUserId());
        }
        List<UserInfo> userInfoList = new ArrayList<>();
        if (userIdList.size() > 0) {
            userInfoList = userService.getUserInfoByIds(userIdList);
        }
        //获取关注的粗略列表
        List<UserFollowing> followingList = this.getUserFollowings(userId);
        //关联粉丝的UserFollowing和UserInfo
        for (UserFollowing userFollowing : fansList) {
            for (UserInfo userInfo : userInfoList) {
                if (userFollowing.getUserId().equals(userInfo.getUserId())) {
                    userInfo.setFollowed(false);
                    userFollowing.setUserInfo(userInfo);
                }
            }
            for (UserFollowing following : followingList) {
                if (userFollowing.getFollowingId().equals(following.getFollowingId())) {
                    userFollowing.getUserInfo().setFollowed(true);
                }
            }
        }
        return fansList;
    }

    public List<UserInfo >checkFollowingStatus(List<UserInfo> list, Long userId) {
        List<UserFollowing> userFollowingList = this.getUserFollowings(userId);
        for(UserInfo userInfo:list){
            for(UserFollowing userFollowing:userFollowingList){
                if(userInfo.getUserId().equals(userFollowing.getFollowingId())){
                    userInfo.setFollowed(true);
                }
            }
        }
        return list;
    }
}
