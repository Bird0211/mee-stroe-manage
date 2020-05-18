package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * Manager
 */
@Data
@TableName("t_app_manager")
public class Manager {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    String username;

    String password;

    Long bizId;

    int status;
    
}