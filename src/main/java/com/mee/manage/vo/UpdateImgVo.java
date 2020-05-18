package com.mee.manage.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * UpdateImgVo
 */
@Data
public class UpdateImgVo {

    MultipartFile file;

    String objectName;
    
}