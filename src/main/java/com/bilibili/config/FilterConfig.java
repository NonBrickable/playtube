package com.bilibili.config;

import com.bilibili.common.UserTransmitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 过滤器配置类
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<UserTransmitFilter> userTransmitFilterConfiguration(){
        FilterRegistrationBean<UserTransmitFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new UserTransmitFilter());
        filter.setName("userTransmitFilter");
        filter.addUrlPatterns("/");
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filter;
    }
    @Bean
    public UserTransmitFilter checkUser(){
        return new UserTransmitFilter();
    }
}
