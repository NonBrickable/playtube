package com.playtube.service;

public interface UserCoinService {

    /**
     * 获取用户有多少个硬币
     * @param userId
     * @return
     */
    Long getUserCoinsAmount(Long userId);

    /**
     * 硬币数更新
     * @param userId
     * @param
     */
    void updateUserCoinsAmount(Long userId, Long userCoinsAmount);
}
