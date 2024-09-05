package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 获取文件对应的md5字符串
     *
     * @param file
     * @return
     */
    @PostMapping("/md5files")
    public JsonResponse<String> getFileMD5(MultipartFile file) throws Exception {
        return new JsonResponse<>(fileService.getFileMD5(file));
    }

    /**
     * 断点续传
     *
     * @param file         MultipartFile类型的文件
     * @param fileMd5      文件二进制流形成的Md5字符串
     * @param sliceNo      分片编号
     * @param totalSliceNo 分片总数
     * @return
     * @throws Exception
     */
    @PutMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws Exception {
        return new JsonResponse<>(fileService.uploadFileBySlices(file, fileMd5, sliceNo, totalSliceNo));
    }
}
