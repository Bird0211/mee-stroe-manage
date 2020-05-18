package com.mee.manage.controller;

import com.mee.manage.service.IAliOssService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * ImageUpdateControoler
 */
@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class ImageUpdateControoler extends BaseController {

    @Autowired
    IAliOssService aliOssService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultVo uploadImg(@RequestParam("file") MultipartFile file) {
        ResultVo result = new ResultVo();
        try {
            String fileName = aliOssService.updateImg(file);
            if (fileName != null) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
                result.setData(fileName);
            }
            else
                result.setStatusCode(StatusCode.FAIL.getCode());
        } catch (Exception ex) {
            logger.info("login error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }
    
}