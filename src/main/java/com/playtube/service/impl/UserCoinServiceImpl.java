package com.playtube.service.impl;

import com.playtube.dao.UserCoinDao;
import com.playtube.service.UserCoinService;
import lombok.RequiredArgsConstructor;
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
