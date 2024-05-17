package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.common.UserContext;
import com.playtube.pojo.FollowingGroup;
import com.playtube.pojo.UserFollowing;
import com.playtube.service.FollowingGroupService;
import com.playtube.service.UserFollowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserFollowingController {
    private final UserFollowingService userFollowingService;
    private final FollowingGroupService followingGroupService;

    /**
     * 新增关注用户
     * @param userFollowing
     * @return
     */
    @PostMapping("/add-following")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing) {
        long userId = UserContext.getUserId();
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }


    /**
     * 获取关注用户（按照分组）
     * @return
     */
    @GetMapping("/following-list")
    public JsonResponse<List<FollowingGroup>> getUserFollowings() {
        long userId = UserContext.getUserId();
        List<FollowingGroup> list = userFollowingService.getUserFollowings(userId);
        return new JsonResponse<>(list);
    }

    /**
     * 获取粉丝列表
     * @return
     */
    @GetMapping("/fans-list")
    public JsonResponse<List<UserFollowing>> getUserFans() {
        long userId = UserContext.getUserId();
        List<UserFollowing> list = userFollowingService.getUserFans(userId);
        return new JsonResponse<>(list);
    }

    /**
     * 添加关注分组
     * @param followingGroup
     * @return
     */
    @PostMapping("/add-following-group")
    public JsonResponse<Long> addUserFollowingGroup(@RequestBody FollowingGroup followingGroup) {
        Long userId = UserContext.getUserId();
        followingGroup.setUserId(userId);
        Long id = followingGroupService.addUserFollowingGroup(followingGroup);
        return new JsonResponse<>(id);
    }

    /**
     * 删除关注分组
     * @param followingGroup
     * @return
     */
    @DeleteMapping("/del-following-group")
    public JsonResponse<String> deleteUserFollowingGroup(@RequestBody FollowingGroup followingGroup){
        Long userId = UserContext.getUserId();
        followingGroup.setUserId(userId);
        followingGroupService.deleteUserFollowingGroup(followingGroup);
        return new JsonResponse<>("成功");
    }
    /**
     * 获取关注分组
     * @return
     */
    @GetMapping("/following-group")
    public JsonResponse<List<FollowingGroup>> getFollowingGroupByUserId(){
        Long userId = UserContext.getUserId();
        List<FollowingGroup> followingGroupList = followingGroupService.getFollowingGroupByUserId(userId);
        return new JsonResponse<>(followingGroupList);
    }
}







