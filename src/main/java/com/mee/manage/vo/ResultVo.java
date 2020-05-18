package com.mee.manage.vo;


import com.mee.manage.util.StatusCode;

import lombok.Data;

@Data
public class ResultVo {

    private String description;
    private int statusCode;
    private Object data;

    public void setStatusCodeEmn(StatusCode code) {
        this.statusCode = code.getCode();
        this.description = code.getCodeMsg();
    }
}
