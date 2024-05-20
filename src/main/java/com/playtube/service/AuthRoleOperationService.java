package com.playtube.service;

import com.playtube.pojo.auth.AuthRoleOperation;

import java.util.List;

public interface AuthRoleOperationService {

    /**
     * 根据用户角色id获取用户操作权限
     * @param roleIdList
     * @return
     */
    List<AuthRoleOperation> getRoleOperationsByRoleIds(List<Long> roleIdList);
}
