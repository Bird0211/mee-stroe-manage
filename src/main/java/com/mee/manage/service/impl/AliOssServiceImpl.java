package com.mee.manage.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.mee.manage.config.Config;
import com.mee.manage.service.IAliOssService;
import com.mee.manage.util.StrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * AliOssServiceImpl
 */
@Service
public class AliOssServiceImpl implements IAliOssService {

    public static final Logger logger = LoggerFactory.getLogger(IAliOssService.class);

    @Autowired
    Config config;

    @Override
    public String updateImg(MultipartFile file) {

        String objectName = StrUtil.getUUID()+".jpg";

        logger.info("objectName : {}", objectName);
        logger.info("OssInfo: {}", config.getSso());
        
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(config.getSso().getEndpoint(), config.getSso().getAccessKeyId(), config.getSso().getAccessKeySecret());

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        try {
            ossClient.putObject(config.getSso().getBucketName(), objectName, file.getInputStream());
        } catch (OSSException e) {
            e.printStackTrace();
            objectName = null;
        } catch (ClientException e) {
            e.printStackTrace();
            objectName = null;
        } catch (IOException e) {
            e.printStackTrace();
            objectName = null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return objectName;
    }

    @Override
    public String getImgUrl(String fileName) {
        if(fileName == null) {
            return null;
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(config.getSso().getEndpoint(), config.getSso().getAccessKeyId(), config.getSso().getAccessKeySecret());

        // 设置URL过期时间为1小时。
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(config.getSso().getBucketName(), fileName, expiration);
        // 关闭OSSClient。
        ossClient.shutdown();
        logger.info("img Url", url);
        return url.toString();
    }
}