package com.mee.manage.vo;

import lombok.Data;

/**
 * AuthVo
 */
@Data
public class AuthVo {

    AuthVo(){

    }

    public AuthVo(Long bizId, Long userId, String token) {
        setBizId(bizId);
        setToken(token);
        setUserId(userId);
    }

    Long bizId;

    Long userId;

    String token;
    
}