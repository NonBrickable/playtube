package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.common.PageResult;
import com.playtube.common.UserContext;
import com.playtube.pojo.Video;
import com.playtube.pojo.VideoCoin;
import com.playtube.pojo.VideoCollection;
import com.playtube.pojo.VideoComment;
import com.playtube.service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    /**
     * 视频投稿
     * @param video
     * @return
     */
    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video) {
        Long userId = UserContext.getUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        return JsonResponse.success();
    }

    /**
     * 瀑布流分页查询
     * @param size 本页的视频数量
     * @param no   本页的编号
     * @param area 分区类型
     * @return
     */
    @GetMapping("/videos/page")
    public JsonResponse<PageResult<Video>> pageListVideos(@RequestParam Integer size, @RequestParam Integer no, String area) {
        PageResult<Video> result = videoService.pageListVideos(size, no, area);
        return new JsonResponse<>(result);
    }

    /**
     * 视频分片在线播放
     * @param request
     * @param response
     * @param path
     * @throws Exception
     */
    @GetMapping("/video-slices")
    public void viewVideosOnlineBySlices(HttpServletRequest request,
                                         HttpServletResponse response,
                                         String path) throws Exception {
        videoService.viewVideosOnlineBySlices(request, response, path);
    }

    /**
     * 视频点赞
     * @param videoId
     * @return
     */
    @PutMapping("/video-like")
    public JsonResponse<String> addVideoLike(@RequestParam Long videoId) {
        long userId = UserContext.getUserId();
        videoService.addVideoLike(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 取消点赞视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/video-like-del")
    public JsonResponse<String> deleteVideoLike(@RequestParam Long videoId) {
        long userId = UserContext.getUserId();
        videoService.deleteVideoLike(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 查询视频点赞数量
     *
     * @param videoId
     * @return
     */
    @GetMapping("/video-like-count")
    public JsonResponse<Map<String, Object>> getVideoLikes(@RequestParam Long videoId) {
        Long userId = -1L;
        try {
            userId = UserContext.getUserId();
        } catch (Exception e) {
        }
        Map<String, Object> result = videoService.getVideoLikes(userId, videoId);
        return new JsonResponse<>(result);
    }

    /**
     * 收藏视频
     *
     * @param videoCollection
     * @return
     */
    @PostMapping("/video-collection")
    public JsonResponse<String> addVideoCollection(@RequestBody VideoCollection videoCollection) {
        Long userId = UserContext.getUserId();
        videoCollection.setUserId(userId);
        videoService.addVideoCollection(videoCollection);
        return JsonResponse.success();
    }

    /**
     * 取消收藏视频
     *
     * @param videoId
     * @return
     */
    @DeleteMapping("/video-collection-del")
    public JsonResponse<String> deleteVideoCollection(@RequestParam Long videoId) {
        Long userId = UserContext.getUserId();
        videoService.deleteVideoCollection(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 查看视频收藏数量
     * @param videoId
     * @return
     */
    @GetMapping("/video-collection-count")
    public JsonResponse<Map<String, Object>> getVideoCollections(@RequestParam Long videoId) {
        Long userId = -1L;
        try {
            userId = UserContext.getUserId();
        } catch (Exception e) {
        }
        Map<String, Object> result = videoService.getVideoCollections(videoId, userId);
        return new JsonResponse<>(result);
    }

    /**
     * 视频投币
     * @param videoCoin
     * @return
     */
    @PostMapping("/video-coins")
    public JsonResponse<String> addVideoCoins(@RequestBody VideoCoin videoCoin) {
        Long userId = UserContext.getUserId();
        videoCoin.setUserId(userId);
        videoService.addVideoCoins(videoCoin);
        return JsonResponse.success();
    }

    /**
     * 查看视频投币数量
     * @param videoId
     * @return
     */
    @GetMapping("/video-coins-count")
    public JsonResponse<Map<String, Object>> getVideoCoins(@RequestParam Long videoId) {
        Long userId = -1L;
        try {
            userId = UserContext.getUserId();
        } catch (Exception e) {
        }
        Map<String, Object> result = videoService.getVideoCoins(videoId, userId);
        return new JsonResponse<>(result);
    }

    /**
     * 添加视频评论
     * @param videoComment
     * @return
     */
    @PostMapping("/video-comment")
    public JsonResponse<String> addVideoComment(@RequestBody VideoComment videoComment) {
        Long userId = UserContext.getUserId();
        videoService.addVideoComment(videoComment, userId);
        return JsonResponse.success();
    }

    /**
     * 分页查看评论
     * @param size
     * @param no
     * @param videoId
     * @return
     */
    @GetMapping("/video-comments-page")
    public JsonResponse<PageResult<VideoComment>> pageListVideoComments(@RequestParam Integer size,
                                                                        @RequestParam Integer no,
                                                                        @RequestParam Long videoId) {
        PageResult<VideoComment> result = videoService.pageListVideoComments(size, no, videoId);
        return new JsonResponse<>(result);
    }

    /**
     * 视频详情
     * @param videoId
     * @return
     */
    @GetMapping("/video-details")
    public JsonResponse<Map<String,Object>> getVideoDetails(@RequestParam Long videoId){
        Map<String,Object> result = videoService.getVideoDetails(videoId);
        return new JsonResponse<>(result);
    }
}
