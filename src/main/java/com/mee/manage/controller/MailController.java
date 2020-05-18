package com.mee.manage.controller;

import com.mee.manage.service.IMailService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.MailParam;
import com.mee.manage.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class MailController extends BaseController {

    @Autowired
    IMailService mailService;

    //邮件的发送者
    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping(value = "/mail/send", method = RequestMethod.POST)
    public ResultVo addProduct(@RequestBody MailParam param) {
        ResultVo result = new ResultVo();
        try {
            mailService.sendTemplateMail(from, param.getTo(),param.getSubject(),param.getModel(),param.getTemplateName());
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }
}
