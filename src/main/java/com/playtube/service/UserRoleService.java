package com.playtube.service;

import com.playtube.pojo.auth.UserRole;

import java.util.List;

public interface UserRoleService {

    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    List<UserRole> getUserRole(Long userId);

    /**
     * 给用户设置默认角色
     * @param id
     */
    void setDefaultRole(Long id);
}
