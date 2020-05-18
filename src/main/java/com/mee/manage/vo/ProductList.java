package com.mee.manage.vo;

import com.mee.manage.po.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductList {
    Long total;

    Long pages;

    Integer pageNo;

    Integer pageRows;

    List<Product> products;

    public static ProductList getEmptyEntry() {
        ProductList entry = new ProductList();
        entry.setTotal(0L);
        entry.setPages(0L);
        entry.setPageRows(0);
        entry.setPageNo(0);
        entry.setProducts(null);
        return entry;
    }

}
