package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * Biz
 */
@Data
@TableName("t_app_biz")
public class Biz {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    String name;

    String code;

    String token;
    
}