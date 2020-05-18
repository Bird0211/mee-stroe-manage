package com.mee.manage.controller;

import com.mee.manage.exception.MeeException;
import com.mee.manage.service.IAuthService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.AuthVo;
import com.mee.manage.vo.LoginData;
import com.mee.manage.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AuthController
 */
@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class AuthController extends BaseController {

    @Autowired
    IAuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVo login(@RequestBody LoginData loginData) {
        ResultVo result = new ResultVo();
        try {
            AuthVo authVo = authService.login(loginData);
            result.setData(authVo);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (MeeException ex) {
            result.setStatusCodeEmn(ex.getStatusCode());
        } catch (Exception ex) {
            logger.info("login error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }
}