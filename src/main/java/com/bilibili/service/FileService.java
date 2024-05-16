package com.bilibili.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 获取文件对应的md5字符串
     * @param file
     * @return
     */
    String getFileMD5(MultipartFile file) throws Exception;

    /**
     * 断点续传
     * @param file MultipartFile类型的文件
     * @param fileMd5 文件二进制流形成的Md5字符串
     * @param sliceNo 分片编号
     * @param totalSliceNo 分片总数
     */
    String uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws Exception;
}
