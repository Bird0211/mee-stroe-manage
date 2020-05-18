package com.mee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.Manager;

/**
 * IManagerService
 */
public interface IManagerService extends IService<Manager> {

    Manager getManager(String username, Long bizId);

}