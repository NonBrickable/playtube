package com.playtube.common;

import com.playtube.common.constant.RedisCacheConstant;
import com.playtube.common.exception.ConditionException;
import com.playtube.util.TokenUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息传输过滤器
 */
public class UserTransmitFilter implements Filter {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        if(redisTemplate.hasKey(RedisCacheConstant.USER_LOGOUT + token)){
            throw new ConditionException("token过期");
        }
        Long userId = TokenUtil.verifyToken(token);
        System.out.println(userId);
        UserContext.setUser(userId);
        try {
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            UserContext.removeUser();
        }
    }
}
