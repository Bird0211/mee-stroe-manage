package com.mee.manage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mee")
@Data
public class Config {

    SSoConfig sso;

    String addressFile;
    
}
