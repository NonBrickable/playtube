package com.playtube.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户动态表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMoments {
    private Long id;
    private Long userId;
    private String type;
    private Long contentId;
    private Date createTime;
    private Date updateTime;
}
