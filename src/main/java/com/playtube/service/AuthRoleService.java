package com.playtube.service;

import com.playtube.pojo.auth.AuthRoleMenu;
import com.playtube.pojo.auth.AuthRoleOperation;

import java.util.List;

public interface AuthRoleService {

    /**
     * 注册设置默认角色
     * @param roleCode
     * @return
     */
    Long getIdByRoleCode(String roleCode);

    /**
     * 获取角色可操作内容
     * @param roleIdList
     * @return
     */
    List<AuthRoleOperation> getRoleOperations(List<Long> roleIdList);

    /**
     * 获取角色可打开的菜单
     * @param roleIdList
     * @return
     */
    List<AuthRoleMenu> getAuthRoleMenus(List<Long> roleIdList);
}
