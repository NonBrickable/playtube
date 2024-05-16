package com.bilibili.service;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.common.JsonResponse;
import com.bilibili.common.PageResult;
import com.bilibili.pojo.User;
import com.bilibili.pojo.UserInfo;

import java.util.Map;

public interface UserService {

    /**
     * 注册
     * @param user
     * @return
     */
    JsonResponse<String> addUser(User user);

    /**
     * 获取用户信息
     * @return
     */
    User getUserInfo(Long userId);

    /**
     * 更新用户基本信息
     * @param user
     * @return
     */
    void updateUsers(User user) throws Exception;

    /**
     * 更新用户详细信息
     * @param userInfo
     * @return
     */
    void updateUserInfos(UserInfo userInfo);

    /**
     * 分页查询用户信息
     * @param params
     * @return
     */
    PageResult<UserInfo> pageListUserInfos(JSONObject params);

    /**
     * 双令牌登录
     * @param user
     * @return
     */
    Map<String, Object> loginForDts(User user) throws Exception;

    /**
     * 登出
     * @param accessToken
     * @param refreshToken
     * @param userId
     */
    void logout(String accessToken, String refreshToken, Long userId);

    /**
     * 刷新token
     * @param refreshToken
     * @param userId
     * @return
     */
    Map<String, String> refreshAccessToken(String refreshToken, Long userId) throws Exception;
}
