package com.playtube.service;

import com.playtube.common.JsonResponse;
import com.playtube.pojo.User;
import com.playtube.pojo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

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
     * 双令牌登录
     * @param user
     * @return
     */
    Map<String, Object> loginForDts(User user) throws Exception;

    /**
     * 登出
     */
    void logout(HttpServletRequest request);

    /**
     * 刷新token
     * @return
     */
    Map<String, String> refreshAccessToken(HttpServletRequest request) throws Exception;

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
