package com.mee.manage.service.impl;

import java.util.concurrent.ExecutionException;

import com.mee.manage.service.GuavaCache;
import com.mee.manage.service.IFileHandle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileHandleServiceImpl implements IFileHandle {

    public static final Logger logger = LoggerFactory.getLogger(IFileHandle.class);

    @Autowired
    GuavaCache guavaCache;

    @Override
    public void handle(String line) {
        String[] strs = line.split(",");
        if (strs.length > 16) {
            String street = strs[14];
            String subub = strs[15];
            String city = strs[16];
            if (StringUtils.isNotEmpty(city) && StringUtils.isNotEmpty(subub) && street.startsWith("\"")
                    && subub.indexOf('"') < 0 && city.endsWith("\"")) {
                // saveStreet(street);
                saveCity(city+','+subub);
            }
        }
        strs = null;
    }

    /**
    private void saveStreet(String street) {
        try {
            guavaCache.putValue(GuavaCache.STEET_KEY, street);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
    }
    */
    private void saveCity(String city) {
        try {
            guavaCache.putValue(GuavaCache.CITY_KEY, city);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}