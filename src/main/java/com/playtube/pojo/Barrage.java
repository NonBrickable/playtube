package com.playtube.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Barrage {
    private Long id;
    private Long videoId;
    private Long userId;
    private String content;
    private String occurTime;
    private Date createTime;
}

