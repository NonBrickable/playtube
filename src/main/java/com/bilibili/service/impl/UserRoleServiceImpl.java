package com.bilibili.service.impl;


import com.bilibili.common.constant.AuthRoleConstant;
import com.bilibili.dao.UserRoleDao;
import com.bilibili.pojo.auth.UserRole;
import com.bilibili.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-角色关联
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleDao userRoleDao;
    private final AuthRoleServiceImpl authRoleService;

    /**
     * 获取useId对应的所有角色
     * @param userId
     * @return
     */
    public List<UserRole> getUserRole(long userId) {
        List<UserRole> userRoleList = userRoleDao.getUserRole(userId);
        return userRoleList;
    }

    /**
     * 注册设置默认角色
     * @param userId
     */
    public void setDefaultRole(Long userId) {
        Long roleId = authRoleService.getIdByRoleCode(AuthRoleConstant.ROLE_LV0);
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleDao.setDefaultRole(userRole);
    }
}
