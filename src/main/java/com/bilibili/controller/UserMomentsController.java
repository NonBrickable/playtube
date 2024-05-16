package com.bilibili.controller;

import com.bilibili.common.JsonResponse;
import com.bilibili.controller.support.UserSupport;
import com.bilibili.pojo.User;
import com.bilibili.pojo.UserInfo;
import com.bilibili.pojo.UserMoments;
import com.bilibili.service.UserMomentsService;
import com.bilibili.service.impl.UserMomentsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserMomentsController {
    private final UserSupport userSupport;
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
        Long userId = userSupport.getCurrentUserId();
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
        Long userId = userSupport.getCurrentUserId();
        List<UserMoments> list = userMomentsService.getUserSubscribedMoments(userId,start,end);
        return new JsonResponse<>(list);
    }
}
