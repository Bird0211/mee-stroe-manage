package com.mee.manage.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * AliOssService
 */
public interface IAliOssService {

    String updateImg(MultipartFile file);

    String getImgUrl(String fileName);
    
}