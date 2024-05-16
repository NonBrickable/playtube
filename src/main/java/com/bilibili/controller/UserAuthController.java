package com.bilibili.controller;

import com.bilibili.common.JsonResponse;
import com.bilibili.controller.support.UserSupport;
import com.bilibili.pojo.auth.UserAuthorities;
import com.bilibili.service.UserAuthService;
import com.bilibili.service.impl.UserAuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAuthController {
    private final UserSupport userSupport;
    private final UserAuthService userAuthService;

    /**
     * 获取用户所有权限
     * @return
     */
    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities(){
        long userId = userSupport.getCurrentUserId();
        //根据userId获取所有权限
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthorities);
    }
}
