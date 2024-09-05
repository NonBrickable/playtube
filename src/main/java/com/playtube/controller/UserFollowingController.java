package com.playtube.controller;

import com.playtube.common.JsonResponse;
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
     *
     * @param userFollowing
     * @return
     */
    @PostMapping("/add-following")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing) {
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }


    /**
     * 获取关注用户（按照分组）
     *
     * @return
     */
    @GetMapping("/following-list")
    public JsonResponse<List<FollowingGroup>> getUserFollowings() {
        return new JsonResponse<>(userFollowingService.getUserFollowings());
    }

    /**
     * 获取粉丝列表
     *
     * @return
     */
    @GetMapping("/fans-list")
    public JsonResponse<List<UserFollowing>> getUserFans() {
        return new JsonResponse<>(userFollowingService.getUserFans(null));
    }

    /**
     * 添加关注分组
     *
     * @param followingGroup
     * @return
     */
    @PostMapping("/add-following-group")
    public JsonResponse<Long> addUserFollowingGroup(@RequestBody FollowingGroup followingGroup) {
        return new JsonResponse<>(followingGroupService.addUserFollowingGroup(followingGroup));
    }

    /**
     * 删除关注分组
     *
     * @param followingGroup
     * @return
     */
    @DeleteMapping("/del-following-group")
    public JsonResponse<String> deleteUserFollowingGroup(@RequestBody FollowingGroup followingGroup) {
        followingGroupService.deleteUserFollowingGroup(followingGroup);
        return JsonResponse.success();
    }

    /**
     * 获取关注分组
     *
     * @return
     */
    @GetMapping("/following-group")
    public JsonResponse<List<FollowingGroup>> getFollowingGroupByUserId() {
        return new JsonResponse<>(followingGroupService.getFollowingGroupByUserId());
    }
}







