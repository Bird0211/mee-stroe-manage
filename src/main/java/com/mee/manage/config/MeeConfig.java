package com.mee.manage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * MeeConfig
 */
@Component
@ConfigurationProperties(prefix = "mee")
@Data
public class MeeConfig {

    SSoConfig sso;
    
}