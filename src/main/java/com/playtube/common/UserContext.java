package com.playtube.common;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Optional;

/**
 * 用户上下文
 */
public class UserContext {

    private static final ThreadLocal<Long> USER_THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 设置用户至上下文
     *
     * @param userId
     */
    public static void setUser(Long userId) {
        USER_THREAD_LOCAL.set(userId);
    }

    /**
     * 从上下文获取用户id
     *
     * @return
     */
    public static Long getUserId() {
        Long userId = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userId).orElse(null);
    }

    /**
     * 移除用户上下文
     */
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }

}
