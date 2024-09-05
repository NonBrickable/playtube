package com.playtube.common;

import com.playtube.common.constant.RedisCacheConstant;
import com.playtube.common.exception.ConditionException;
import com.playtube.util.TokenUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final RedisTemplate<String,String> redisTemplate;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();
        Set<String> set = new HashSet<>();
        set.add("/register");
        set.add("/user-dts");
        if(set.contains(path)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
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
