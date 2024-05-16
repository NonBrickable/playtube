package com.bilibili.controller;

import com.bilibili.common.JsonResponse;
import com.bilibili.common.UserContext;
import com.bilibili.pojo.auth.UserAuthorities;
import com.bilibili.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAuthController {
    private final UserAuthService userAuthService;

    /**
     * 获取用户所有权限
     * @return
     */
    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities(){
        long userId = UserContext.getUserId();
        //根据userId获取所有权限
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthorities);
    }
}
