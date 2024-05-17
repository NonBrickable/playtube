package com.playtube.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthRoleDao {

    Long getIdByRoleCode(@Param("roleCode") String roleCode);
}
