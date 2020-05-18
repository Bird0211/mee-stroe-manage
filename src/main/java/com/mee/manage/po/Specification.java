package com.mee.manage.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Specification
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_app_specification")
public class Specification {

    @TableId(type = IdType.ID_WORKER)    
    Long id;

    Long productId;

    String name;

    BigDecimal price;

    int weight;

    String barcode;

    int number;

    String image;

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        Specification specification = (Specification)obj;
        if(getImage() == null && specification.getImage() != null) {
            return false;
        }
        if(getImage() != null && !getImage().equals(specification.getImage())) {
            return false;
        }

        if(getName() == null && specification.getName() != null) {
            return false;
        }

        if(getName() != null && !getName().equals(specification.getName())) {
            return false;
        }

        if(getBarcode() == null && specification.getBarcode() != null) {
            return false;
        }

        if(getBarcode() != null && !getBarcode().equals(specification.getBarcode())) {
            return false;
        }

        if(getNumber() != specification.getNumber()) {
            return false;
        }

        if(getPrice() == null && specification.getPrice() != null) {
            return false;
        }

        if(getPrice().compareTo(specification.getPrice()) != 0) {
            return false;
        }

        if(getProductId().compareTo(specification.id) != 0) {
            return false;
        }

        if(getWeight() != specification.getWeight()) {
            return false;
        }

        return true;
    }
}