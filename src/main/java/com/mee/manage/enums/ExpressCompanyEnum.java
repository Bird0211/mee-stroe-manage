package com.mee.manage.enums;

public enum ExpressCompanyEnum {
    NZ_POST("1001","NZ POST");

    String code;
    String name;

    ExpressCompanyEnum(String code,String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static String getCompanyEnum(String code) {
        for(ExpressCompanyEnum companyEnum : values()) {
            if(companyEnum.getCode().equals(code))
                return companyEnum.getName();
        } 
        return null;
    }

}