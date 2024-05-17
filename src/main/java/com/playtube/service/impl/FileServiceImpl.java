package com.playtube.service.impl;

import com.playtube.dao.FileDao;
import com.playtube.pojo.File;
import com.playtube.service.FileService;
import com.playtube.util.FastDFSUtil;
import com.playtube.util.MD5Util;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FastDFSUtil fastDFSUtil;
    private final FileDao fileDao;
    public String uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws Exception {
        File dbFileMD5 = fileDao.getFileByMD5(fileMd5);
        if(dbFileMD5 != null){
            return dbFileMD5.getUrl();
        }
        String url = fastDFSUtil.uploadFileBySlices(file,fileMd5,sliceNo,totalSliceNo);
        //判断是不是最后一次上传
        if(!StringUtil.isNullOrEmpty(url)){
            dbFileMD5 = new File();
            dbFileMD5.setMd5(fileMd5);
            dbFileMD5.setUrl(url);
            dbFileMD5.setType(fastDFSUtil.getFileType(file));
            fileDao.addFile(dbFileMD5);
        }
        return url;
    }
    public String getFileMD5(MultipartFile file) throws Exception {
        return MD5Util.getFileMD5(file);
    }
}
