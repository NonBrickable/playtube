package com.playtube.service.impl;

import com.playtube.common.JsonResponse;
import com.playtube.common.UserContext;
import com.playtube.common.constant.RedisCacheConstant;
import com.playtube.common.constant.UserConstant;
import com.playtube.dao.UserDao;
import com.playtube.common.exception.ConditionException;
import com.playtube.pojo.*;
import com.playtube.service.UserRoleService;
import com.playtube.service.UserService;
import com.playtube.util.MD5Util;
import com.playtube.util.RSAUtil;
import com.playtube.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserRoleService userRoleService;
    private final RedisTemplate<String,String> redisTemplate;

    /**
     * 注册
     * @param user
     * @return
     */
    public JsonResponse<String> addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        User dbuser = this.getUserByphone(phone);
        if (dbuser != null) {
            throw new ConditionException("该手机号已经注册");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();//获取密码
        String rawpassword;
        try {
            rawpassword = RSAUtil.decrypt(password);//获得经过MD5加密接着进行解密后的密码
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        //将创建数据放入User
        String md5password = MD5Util.sign(rawpassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5password);
        userDao.addUser(user);
        //添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userDao.addUserInfo(userInfo);
        userRoleService.setDefaultRole(user.getId());
        return JsonResponse.success("成功");
    }

    public User getUserByphone(String phone) {
        return userDao.getUserByphone(phone);
    }

    /**
     * 获取用户信息
     * @param
     * @return
     */
    public User getUserInfo() {
        Long userId = UserContext.getUserId();
        User user = userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoById(userId);
        user.setUserInfo(userInfo);
        return user;
    }


    //更新用户基本信息
    public void updateUsers(User user) throws Exception {
        Long userId = UserContext.getUserId();
        User dbUser = userDao.getUserById(userId);
        if (dbUser == null) {
            throw new ConditionException("用户不存在");
        }
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }
        userDao.updateUsers(user);
    }

    //更新用户详细信息
    public void updateUserInfos(UserInfo userInfo) {
        Long userId = UserContext.getUserId();
        userInfo.setUserId(userId);
        userDao.updateUserInfos(userInfo);
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    public List<UserInfo> getUserInfoByIds(List<Long> followingIdList) {
        return userDao.getUserInfoByUserIds(followingIdList);
    }

    /**
     * 双令牌登录
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional
    public Map<String, Object> loginForDts(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        User dbuser = this.getUserByphone(phone);
        if (dbuser == null) {
            throw new ConditionException("该用户未注册");
        }
        String password = user.getPassword();//获取前端传过来的密码
        String rawpassword;
        try {
            rawpassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        String salt = dbuser.getSalt();
        String md5password = MD5Util.sign(rawpassword, salt, "UTF-8");
        if (!md5password.equals(dbuser.getPassword())) {
            throw new ConditionException("密码错误");
        }
        Long userId = dbuser.getId();
        String accessToken = TokenUtil.generateToken(userId);
        String refreshToken = TokenUtil.generateRefreshToken(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }

    /**
     * 登出
     */
    public void logout(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        String refreshToken = request.getHeader("refreshToken");
        Long userId = UserContext.getUserId();
        redisTemplate.opsForValue().set(RedisCacheConstant.USER_LOGOUT + accessToken,userId.toString(),1, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisCacheConstant.USER_LOGOUT + refreshToken,userId.toString(),7, TimeUnit.DAYS);
    }

    /**
     * 刷新accessToken
     * @return
     */
    public Map<String, String> refreshAccessToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("token");
        Long userId = UserContext.getUserId();
        redisTemplate.opsForValue().set(RedisCacheConstant.USER_LOGOUT + refreshToken,userId.toString(),7,TimeUnit.DAYS);
        String accessToken = TokenUtil.generateToken(userId);
        String newRefreshToken = TokenUtil.generateRefreshToken(userId);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken",accessToken);
        tokenMap.put("refreshToken",newRefreshToken);
        return tokenMap;
    }
    public List<UserInfo> batchGetUserInfo(Set<Long> userIdList) {
        return userDao.batchGetUserInfo(userIdList);
    }
}
