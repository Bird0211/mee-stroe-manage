package com.mee.manage.service.impl;

import java.nio.charset.StandardCharsets;

import com.mee.manage.service.IBigFileReaderService;
import com.mee.manage.service.IFileHandle;
import com.mee.manage.util.BigFileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BigFileReaderServiceImpl implements IBigFileReaderService {

    @Autowired
    IFileHandle fileHander;

    @Override
    public void BigCSVFile(String bigFilePath) {
       
        BigFileReader.Builder builder = new BigFileReader.Builder(bigFilePath,fileHander);
        BigFileReader bigFileReader = builder
                .threadPoolSize(2000)
                .charset(StandardCharsets.UTF_8)
                .bufferSize(1024 * 1024).build();
        bigFileReader.start();
    }

}