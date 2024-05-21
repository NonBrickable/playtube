package com.playtube.dao;

import com.playtube.pojo.UserMoments;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMomentsDao {
    Integer addUserMoments(UserMoments userMoments);

    List<UserMoments> getUserMomentsByUserId(List<Long> followingUserList);
}
