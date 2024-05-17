package com.playtube.service;

import com.playtube.pojo.auth.UserAuthorities;

public interface UserAuthService {

    /**
     * 根据userId获取所有权限
     * @param userId
     * @return
     */
    UserAuthorities getUserAuthorities(long userId);
}
