package com.mee.manage.service.impl;

import java.util.UUID;

import com.mee.manage.exception.MeeException;
import com.mee.manage.po.Biz;
import com.mee.manage.po.Manager;
import com.mee.manage.service.IAuthService;
import com.mee.manage.service.IBizService;
import com.mee.manage.service.IManagerService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.AuthVo;
import com.mee.manage.vo.LoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl
 */
@Service
public class AuthServiceImpl implements IAuthService{

    @Autowired
    IBizService bizService;

    @Autowired
    IManagerService managerService;

    public AuthVo login(LoginData data) throws MeeException {
        if(data == null) {
            throw new MeeException(StatusCode.PARAM_ERROR);
        }

        Biz bizInfo = bizService.getBiz(data.getCode());
        if (bizInfo == null)
            throw new MeeException(StatusCode.BIZ_NOT_EXIST);

        Manager manager = managerService.getManager(data.getUserName(), bizInfo.getId());
        if(manager == null) {
            throw new MeeException(StatusCode.USER_NOT_EXIST);
        }

        if (!manager.getPassword().equals(data.getPassword())) {
            throw new MeeException(StatusCode.PSW_ERROR);
        }

        if(manager.getStatus() != 0) {
            throw new MeeException(StatusCode.AUTH_FAIL);
        }

        return new AuthVo(bizInfo.getId(), manager.getId(), UUID.randomUUID().toString());
    }
    
}