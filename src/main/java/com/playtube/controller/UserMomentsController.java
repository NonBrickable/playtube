package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.common.UserContext;
import com.playtube.pojo.UserMoments;
import com.playtube.service.UserMomentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserMomentsController {
    private final UserMomentsService userMomentsService;
    private final RedisTemplate<String,String> redisTemplate;

    /**
     * 新增动态
     * @param userMoments
     * @return
     * @throws Exception
     */
//    @ControllerLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
//    @DataLimited
    @PostMapping("/add-moment")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoments userMoments) throws Exception {
        Long userId = UserContext.getUserId();
        System.out.println(userId);
        userMoments.setUserId(userId);
        userMomentsService.addUserMoments(userMoments);
        return new JsonResponse<>("新建动态成功");
    }

    /**
     * 获取动态
     * @return
     */
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoments>> getUserSubscribedMoments(@RequestParam(value = "start") Long start,@RequestParam(value = "end") Long end) {
        Long userId = UserContext.getUserId();
        List<UserMoments> list = userMomentsService.getUserSubscribedMoments(userId,start,end);
        return new JsonResponse<>(list);
    }
}
