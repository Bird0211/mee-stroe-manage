package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_app_brand")
public class Brand {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    String brandName;

    Long bizId;

}
