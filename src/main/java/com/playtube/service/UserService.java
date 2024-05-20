package com.playtube.service;

import com.alibaba.fastjson.JSONObject;
import com.playtube.common.JsonResponse;
import com.playtube.common.PageResult;
import com.playtube.pojo.User;
import com.playtube.pojo.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 根据用户id获取用户
     * @param followingId
     * @return
     */
    User getUserById(Long followingId);

    /**
     * 根据关注id获取用户信息
     * @param followingIdList
     * @return
     */
    List<UserInfo> getUserInfoByIds(List<Long> followingIdList);

    /**
     * 批量获取用户信息
     * @param userIdList
     * @return
     */
    List<UserInfo> batchGetUserInfo(Set<Long> userIdList);
}
