package com.playtube.aspect;


import com.playtube.common.UserContext;
import com.playtube.common.constant.AuthRoleConstant;
import com.playtube.common.exception.ConditionException;
import com.playtube.pojo.UserMoments;
import com.playtube.pojo.auth.UserRole;
import com.playtube.service.impl.UserRoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(1)
@Component
@Aspect
@RequiredArgsConstructor
public class DataLimitedAspect {
    private final UserRoleServiceImpl userRoleService;

    //切点，指定注解
    @Pointcut("@annotation(com.playtube.aspect.annotation.DataLimited)")
    public void check() {

    }

    @Before("check()")
    public void doBefore(JoinPoint joinPoint) {
        Long userId = UserContext.getUserId();
        List<UserRole> userRoleList = userRoleService.getUserRole(userId);
        Set roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args){
            if(arg instanceof UserMoments){
                UserMoments userMoments = (UserMoments)arg;
                String type = userMoments.getType();
                //如果权限等级不够，不能访问某些数据
                if(roleCodeSet.contains(AuthRoleConstant.ROLE_LV0) && !"0".equals(type)){
                    throw new ConditionException("参数异常");
                }
            }
        }

    }
}
