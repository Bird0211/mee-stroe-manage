package com.mee.manage.service.impl;

import java.util.List;

import com.mee.manage.config.Config;
import com.mee.manage.po.City;
import com.mee.manage.service.GuavaCache;
import com.mee.manage.service.ICityService;
import com.mee.manage.service.IInitCityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCityServiceImpl implements IInitCityService, ApplicationRunner {

    @Autowired
    Config Config;

    @Autowired
    ICityService cityService;

    @Autowired
    GuavaCache guavaCache;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<City> city = cityService.list();
        guavaCache.setValue(GuavaCache.CITY_KEY, city);
    }
    
}