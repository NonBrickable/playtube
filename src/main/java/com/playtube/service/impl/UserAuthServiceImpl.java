package com.playtube.service.impl;

import com.playtube.pojo.auth.AuthRoleMenu;
import com.playtube.pojo.auth.AuthRoleOperation;
import com.playtube.pojo.auth.UserAuthorities;
import com.playtube.pojo.auth.UserRole;
import com.playtube.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限集成
 */
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRoleServiceImpl userRoleService;
    private final AuthRoleServiceImpl authRoleService;

    /**
     * 获取用户的所有操作权限和菜单权限
     * @param userId
     * @return
     */
    public UserAuthorities getUserAuthorities(long userId) {
        List<UserRole> userRoleList = userRoleService.getUserRole(userId);
        List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //批量查询权限
        List<AuthRoleOperation> authRoleOperationList = authRoleService.getRoleOperations(roleIdList);
        List<AuthRoleMenu> authRoleMenuList = authRoleService.getAuthRoleMenus(roleIdList);
        UserAuthorities userAuthorities = new UserAuthorities(authRoleOperationList,authRoleMenuList);
        return userAuthorities;
    }
}
