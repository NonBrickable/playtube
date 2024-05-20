package com.playtube.service;

import com.playtube.pojo.auth.AuthRoleMenu;

import java.util.List;

public interface AuthRoleMenuService {

    /**
     * 批量获取用户菜单
     * @param roleIdList
     * @return
     */
    List<AuthRoleMenu> getAuthRoleMenus(List<Long> roleIdList);
}
