package com.bilibili.service;

import com.bilibili.pojo.auth.UserAuthorities;

public interface UserAuthService {

    /**
     * 根据userId获取所有权限
     * @param userId
     * @return
     */
    UserAuthorities getUserAuthorities(long userId);
}
