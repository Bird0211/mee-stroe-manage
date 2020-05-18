package com.mee.manage.vo;

import java.util.List;

import com.mee.manage.po.Product;
import com.mee.manage.po.Specification;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ProductParam
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductParam extends Product {

    Long freightId;

    List<Specification> specifications;
    
}