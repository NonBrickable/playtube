package com.playtube.service.impl;
import com.playtube.dao.AuthRoleOperationDao;
import com.playtube.pojo.auth.AuthRoleOperation;
import com.playtube.service.AuthRoleOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色-元素操作关联
 */
@Service
@RequiredArgsConstructor
public class AuthRoleOperationServiceImpl implements AuthRoleOperationService {
    private final AuthRoleOperationDao authRoleOperationDao;
    public List<AuthRoleOperation> getRoleOperationsByRoleIds(List<Long> roleIdList) {
        return authRoleOperationDao.getRoleOperationsByRoleIds(roleIdList);
    }
}
