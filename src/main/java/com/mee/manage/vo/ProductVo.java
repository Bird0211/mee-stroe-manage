package com.mee.manage.vo;

import com.mee.manage.po.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ProductVo extends Product {
    Long freightId;
}