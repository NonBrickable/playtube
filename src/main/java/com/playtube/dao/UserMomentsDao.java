package com.playtube.dao;

import com.playtube.pojo.UserMoments;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMomentsDao {

    Integer addUserMoments(UserMoments userMoments);

    List<UserMoments> getFollowingMoments(Map<String, Long> params);

    List<UserMoments> getOneDayMoments(Long userId);
}
