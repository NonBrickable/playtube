package com.playtube.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class VideoCollection {
    private Long id;
    private Long videoId;
    private Long userId;
    private Long groupId;
    private Date createTime;
}
