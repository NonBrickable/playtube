package com.playtube.dao;

import com.playtube.pojo.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleDao {

    List<UserRole> getUserRole(@Param("userId") Long userId);

    Integer setDefaultRole(UserRole userRole);
}
