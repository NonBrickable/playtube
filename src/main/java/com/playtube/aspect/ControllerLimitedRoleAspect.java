package com.playtube.aspect;

import com.playtube.common.UserContext;
import com.playtube.common.exception.ConditionException;
import com.playtube.aspect.annotation.ControllerLimitedRole;
import com.playtube.pojo.auth.UserRole;
import com.playtube.service.impl.UserRoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(1)
@Component
@Aspect
@RequiredArgsConstructor
public class ControllerLimitedRoleAspect {
    private final UserRoleServiceImpl userRoleService;

    //切入点，指定注解标注的位置
    @Pointcut("@annotation(com.playtube.aspect.annotation.ControllerLimitedRole)")
    public void check() {

    }

    //@Before 前置通知，在方法执行之前
    // 指定方法，获取角色编码
    @Before("check() && @annotation(controllerLimitedRole)")
    public void doBefore(JoinPoint joinPoint, ControllerLimitedRole controllerLimitedRole) {
        Long userId = UserContext.getUserId();
        //用户的角色集合
        List<UserRole> userRoleList = userRoleService.getUserRole(userId);
        //限制使用的角色集合
        String[] limitedRoleCodeList = controllerLimitedRole.limitedRoleCodeList();
        Set limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        //角色编码取交集
        roleCodeSet.retainAll(limitedRoleCodeSet);
        if(roleCodeSet.size()>0){
            throw new ConditionException("权限不足");
        }
    }
}
