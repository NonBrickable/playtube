package com.playtube.service.impl;

import com.playtube.dao.AuthRoleMenuDao;
import com.playtube.pojo.auth.AuthRoleMenu;
import com.playtube.service.AuthRoleMenuService;
import lombok.RequiredArgsConstructor;
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
