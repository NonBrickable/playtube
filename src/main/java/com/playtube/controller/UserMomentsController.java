package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.pojo.UserMoments;
import com.playtube.service.UserMomentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/moment")
@RequiredArgsConstructor
public class UserMomentsController {

    private final UserMomentsService userMomentsService;

    /**
     * 新增动态
     * @param userMoments
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoments userMoments) throws Exception {
        userMomentsService.addUserMoments(userMoments);
        return JsonResponse.success();
    }

    /**
     * 轮询获取1天内发布的动态
     * @return
     */
    @GetMapping("/batch")
    public JsonResponse<List<UserMoments>> getUserSubscribedMoments() {
        return new JsonResponse<>(userMomentsService.getUserSubscribedMoments());
    }

    /**
     * 用户主动获取动态-瀑布流查询
     * @param size 每页多少条
     * @param no 当前页数
     * @return
     */
    @GetMapping("/batch/active")
    public JsonResponse<List<UserMoments>> getUserSubscribedMomentsActive(@RequestParam(value = "size") Long size,@RequestParam(value = "no") Long no) {
        return new JsonResponse<>(userMomentsService.getUserSubscribedMomentsActive(size,no));
    }
}
