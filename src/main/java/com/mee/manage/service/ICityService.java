package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.City;

public interface ICityService extends IService<City> {

    List<String> searchCity(String text, int limit);

    List<String> searchSuburb(String city,String text);

}