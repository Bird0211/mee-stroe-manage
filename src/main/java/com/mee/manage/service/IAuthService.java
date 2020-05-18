package com.mee.manage.service;

import com.mee.manage.vo.AuthVo;
import com.mee.manage.vo.LoginData;

/**
 * AuthService
 */
public interface IAuthService {

    AuthVo login(LoginData data) throws Exception;
}