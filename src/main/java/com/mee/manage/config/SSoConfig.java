package com.mee.manage.config;

import lombok.Data;

/**
 * Config
 */
@Data
public class SSoConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    //外网访问地址
    private String imgUrl;
}