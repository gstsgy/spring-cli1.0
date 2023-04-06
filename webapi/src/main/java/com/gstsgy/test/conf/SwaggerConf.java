package com.gstsgy.test.conf;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConf {
    @Bean
    public GroupedOpenApi springOpenAPI() {
        return GroupedOpenApi.builder()
                .packagesToScan("com.gstsgy.workers.help.controller")
                .group("workers-help接口")
                .build();
    }
}
