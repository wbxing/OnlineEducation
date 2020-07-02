package com.education.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传至阿里云
     *
     * @param file 需要上传的文件
     * @return 返回图片地址
     */
    String upload(MultipartFile file);
}

