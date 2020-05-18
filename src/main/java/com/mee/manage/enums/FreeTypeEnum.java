package com.mee.manage.enums;

public enum FreeTypeEnum {

    NO(0),          //无
    NUMBER(1),     //按件包邮
    PRICE(2);     //按价包邮

    int code;

    FreeTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
    
}