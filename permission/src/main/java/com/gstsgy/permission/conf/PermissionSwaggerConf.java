package com.gstsgy.permission.conf;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/3/3 上午11:36
 */
@Configuration
public class PermissionSwaggerConf {
    @Bean
    public GroupedOpenApi permissionOpenAPI() {
        return GroupedOpenApi.builder()
                .packagesToScan("com.gstsgy.permission.controller")
                .group("权限相关功能接口")
                .build();
    }
}
