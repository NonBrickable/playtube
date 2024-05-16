package com.bilibili.service.impl;

import com.bilibili.dao.UserCoinDao;
import com.bilibili.service.UserCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCoinServiceImpl implements UserCoinService {
    private final UserCoinDao userCoinDao;

    public Long getUserCoinsAmount(Long userId) {
        return userCoinDao.getUserCoinsAmount(userId);
    }

    public void updateUserCoinsAmount(Long userId, Long userCoinsAmount) {
        userCoinDao.updateUserCoinsAmount(userId,userCoinsAmount);
    }
}
