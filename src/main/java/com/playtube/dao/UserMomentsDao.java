package com.playtube.dao;

import com.playtube.pojo.UserMoments;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMomentsDao {
    Integer addUserMoments(UserMoments userMoments);
}
