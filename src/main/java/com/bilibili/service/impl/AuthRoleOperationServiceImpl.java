package com.bilibili.service.impl;
import com.bilibili.dao.AuthRoleOperationDao;
import com.bilibili.pojo.auth.AuthRoleOperation;
import com.bilibili.service.AuthRoleOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
