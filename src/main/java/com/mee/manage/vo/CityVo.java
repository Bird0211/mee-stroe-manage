package com.mee.manage.vo;

import java.util.List;

import lombok.Data;

@Data
public class CityVo {

    String city;

    List<String> suburb;
}