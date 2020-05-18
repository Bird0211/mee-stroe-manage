package com.mee.manage.vo;

import lombok.Data;

/**
 * LoginData
 */
@Data
public class LoginData {
    
    String code;

    String userName;

    String password;

    boolean remember;
    
}