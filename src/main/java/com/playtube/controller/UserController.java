package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.pojo.User;
import com.playtube.pojo.UserInfo;
import com.playtube.service.UserService;
import com.playtube.util.RSAUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 获取RSA公钥
     * @return
     */
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return JsonResponse.success(pk);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public JsonResponse<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public JsonResponse<User> getUserInfo() {
        return new JsonResponse<>(userService.getUserInfo(null));
    }

    /**
     * 更新用户基本信息
     * @param user
     * @return
     * @throws Exception
     */
    @PutMapping("/updateUsers")
    public JsonResponse<String> updateUsers(@RequestBody User user) throws Exception {
        userService.updateUsers(user);
        return JsonResponse.success();
    }

    /**
     * 更新用户详细信息
     * @param userInfo
     * @return
     */
    @PutMapping("/updateUserInfos")
    public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo) {
        userService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }

    /**
     * 双令牌登录
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/user-dts")
    public JsonResponse<Map<String, Object>> loginForDts(@RequestBody User user) throws Exception {
        return new JsonResponse<>(userService.loginForDts(user));
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @PostMapping ("/logout")
    public JsonResponse<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return JsonResponse.success();
    }

    /**
     * 获取新的accessToken————流程：accessToken过期，前端调用生成该接口localhost:
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/new-token")
    public JsonResponse<Map<String,String>> refreshAccessToken(HttpServletRequest request) throws Exception {
        return new JsonResponse<>(userService.refreshAccessToken(request));
    }
}
