package com.zappos.backoffice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spring.datasource")
public class DatasourceProperties {
    private String url;
    private String username;
    private String password;
}
