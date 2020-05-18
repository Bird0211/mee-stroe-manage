package com.mee.manage.service;

import java.util.List;

import com.mee.manage.vo.CityVo;

public interface IStreetService {

    List<String> getStreets(String name);

    List<CityVo> getCities();

}