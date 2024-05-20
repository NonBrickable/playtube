package com.playtube.controller;

import com.alibaba.fastjson.JSONObject;
import com.playtube.common.JsonResponse;
import com.playtube.common.PageResult;
import com.playtube.common.UserContext;
import com.playtube.pojo.User;
import com.playtube.pojo.UserInfo;
import com.playtube.service.UserFollowingService;
import com.playtube.service.UserService;
import com.playtube.util.RSAUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserFollowingService userFollowingService;

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
        Long userId = UserContext.getUserId();
        User user = userService.getUserInfo(userId);
        return new JsonResponse<>(user);
    }

    /**
     * 更新用户基本信息
     * @param user
     * @return
     * @throws Exception
     */
    @PutMapping("/updateUsers")
    public JsonResponse<String> updateUsers(@RequestBody User user) throws Exception {
        Long userId = UserContext.getUserId();
        user.setId(userId);
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
        Long userId = UserContext.getUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }

    /**
     * 分页查询用户信息
     * @param no   当前页码
     * @param size 当前页有多少条数据
     * @param nick 昵称
     * @return
     */
    @GetMapping("/page-user")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no, @RequestParam Integer size, String nick) {
        Long userId = UserContext.getUserId();
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("nick", nick);
        params.put("userId", userId);
        PageResult<UserInfo> result = userService.pageListUserInfos(params);
        if (result.getTotal() > 0) {
            List<UserInfo> userInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(userInfoList);
        }
        return new JsonResponse<>(result);
    }

    /**
     * 双令牌登录
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/user-dts")
    public JsonResponse<Map<String, Object>> loginForDts(@RequestBody User user) throws Exception {
        Map<String, Object> map = userService.loginForDts(user);
        return new JsonResponse<>(map);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @PostMapping ("/logout")
    public JsonResponse<String> logout(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        String refreshToken = request.getHeader("refreshToken");
        Long userId = UserContext.getUserId();
        userService.logout(accessToken, refreshToken, userId);
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
        String refreshToken = request.getHeader("token");
        Long userId = UserContext.getUserId();
        Map<String,String> tokenMap = userService.refreshAccessToken(refreshToken,userId);
        return new JsonResponse<>(tokenMap);
    }
}
