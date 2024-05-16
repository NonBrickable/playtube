package com.bilibili.controller.support;

import com.bilibili.common.RedisCacheConstant;
import com.bilibili.exception.ConditionException;
import com.bilibili.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class UserSupport {
    private final RedisTemplate<String,String> redisTemplate;

    /**
     * 抓取前端请求，验证token并返回userId
     * @return
     */
    public long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        if(redisTemplate.hasKey(RedisCacheConstant.USER_LOGOUT + token)){
            throw new ConditionException("token过期");
        }
        long userId = TokenUtil.verifyToken(token);
        if(userId < 0){
            throw new ConditionException("非法用户");
        }
        return userId;
    }
}
