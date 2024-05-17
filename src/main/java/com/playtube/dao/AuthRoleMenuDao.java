package com.playtube.dao;

import com.playtube.pojo.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AuthRoleMenuDao {
    List<AuthRoleMenu> getAuthRoleMenus(List<Long> roleIdList);
}
