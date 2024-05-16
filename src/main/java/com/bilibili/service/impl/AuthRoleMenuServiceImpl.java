package com.bilibili.service.impl;

import com.bilibili.dao.AuthRoleMenuDao;
import com.bilibili.pojo.auth.AuthRoleMenu;
import com.bilibili.service.AuthRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthRoleMenuServiceImpl implements AuthRoleMenuService {
    private final AuthRoleMenuDao authRoleMenuDao;

    /**
     * 获取角色对应菜单的list
     * @param roleIdList
     * @return
     */
    public List<AuthRoleMenu> getAuthRoleMenus(List<Long> roleIdList) {
        return authRoleMenuDao.getAuthRoleMenus(roleIdList);
    }
}
