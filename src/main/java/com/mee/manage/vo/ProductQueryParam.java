package com.mee.manage.vo;

import lombok.Data;

@Data
public class ProductQueryParam {

    Long sku;

    String name;

    Long brandId;

    Long categoryId;

    Integer pageNo;

    Integer pageRows;

}
