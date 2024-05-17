package com.playtube.service.impl;

import com.playtube.dao.AuthRoleDao;
import com.playtube.pojo.auth.AuthRoleMenu;
import com.playtube.pojo.auth.AuthRoleOperation;
import com.playtube.service.AuthRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthRoleServiceImpl implements AuthRoleService {
    private final AuthRoleOperationServiceImpl authRoleOperationService;
    private final AuthRoleMenuServiceImpl authRoleMenuService;
    private final AuthRoleDao authRoleDao;

    /**
     * 批量获取角色对应的操作权限
     * @param roleIdList
     * @return
     */
    public List<AuthRoleOperation> getRoleOperations(List<Long> roleIdList) {
        return authRoleOperationService.getRoleOperationsByRoleIds(roleIdList);
    }

    /**
     * 批量获取角色对应的菜单
     * @param roleIdList
     * @return
     */
    public List<AuthRoleMenu> getAuthRoleMenus(List<Long> roleIdList) {
        return authRoleMenuService.getAuthRoleMenus(roleIdList);
    }

    public Long getIdByRoleCode(String roleCode) {
        return authRoleDao.getIdByRoleCode(roleCode);
    }
}
