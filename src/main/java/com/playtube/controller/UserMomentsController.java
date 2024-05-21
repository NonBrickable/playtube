package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.pojo.UserMoments;
import com.playtube.service.UserMomentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserMomentsController {
    private final UserMomentsService userMomentsService;

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
        userMomentsService.addUserMoments(userMoments);
        return JsonResponse.success();
    }

    /**
     * 轮询获取动态
     * @return
     */
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoments>> getUserSubscribedMoments(@RequestParam(value = "start") Long start,@RequestParam(value = "end") Long end) {
        return new JsonResponse<>(userMomentsService.getUserSubscribedMoments(start,end));
    }

    /**
     * 用户主动获取动态
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/user-subscribed-moment-active")
    public JsonResponse<List<UserMoments>> getUserSubscribedMomentsActive(@RequestParam(value = "start") Long start,@RequestParam(value = "end") Long end) {
        return new JsonResponse<>(userMomentsService.getUserSubscribedMomentsActive(start,end));
    }
}
