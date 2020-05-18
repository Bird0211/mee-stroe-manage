package com.mee.manage.enums;

public enum ExpressTypeEnum {

    DELIVERY(0),
    PARTIAL_DELIVERY(1);

    int code;

    ExpressTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}