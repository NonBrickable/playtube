package com.playtube.service;

import com.playtube.common.PageResult;
import com.playtube.pojo.Video;
import com.playtube.pojo.VideoCoin;
import com.playtube.pojo.VideoCollection;
import com.playtube.pojo.VideoComment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface VideoService {

    /**
     * 视频投稿
     * @param video
     */
    void addVideos(Video video);

    /**
     * 瀑布流分页查询
     * @param size
     * @param no
     * @param area
     * @return
     */
    PageResult<Video> pageListVideos(Integer size, Integer no, String area);

    /**
     * 视频分片在线播放
     * @param request
     * @param response
     * @param path
     */
    void viewVideosOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String path) throws Exception;

    /**
     * 视频点赞
     * @param videoId
     * @param userId
     */
    void addVideoLike(Long videoId, long userId);

    /**
     * 取消点赞视频
     * @param videoId
     * @param userId
     */
    void deleteVideoLike(Long videoId, long userId);

    /**
     * 查看视频点赞数
     * @param userId
     * @param videoId
     * @return
     */
    Map<String, Object> getVideoLikes(Long userId, Long videoId);

    /**
     * 收藏视频
     * @param videoCollection
     */
    void addVideoCollection(VideoCollection videoCollection);

    /**
     * 取消视频收藏
     * @param videoId
     * @param userId
     */
    void deleteVideoCollection(Long videoId, Long userId);

    /**
     * 查看视频收藏数量
     * @param videoId
     * @param userId
     * @return
     */
    Map<String, Object> getVideoCollections(Long videoId, Long userId);

    /**
     * 视频投币
     * @param videoCoin
     */
    void addVideoCoins(VideoCoin videoCoin);

    /**
     * 查看视频投币数量
     * @param videoId
     * @param userId
     * @return
     */
    Map<String, Object> getVideoCoins(Long videoId, Long userId);

    /**
     * 添加视频评论
     * @param videoComment
     * @param userId
     */
    void addVideoComment(VideoComment videoComment, Long userId);

    /**
     * 分页查看评论
     * @param size
     * @param no
     * @param videoId
     * @return
     */
    PageResult<VideoComment> pageListVideoComments(Integer size, Integer no, Long videoId);

    /**
     * 视频详情
     * @param videoId
     * @return
     */
    Map<String, Object> getVideoDetails(Long videoId);
}
