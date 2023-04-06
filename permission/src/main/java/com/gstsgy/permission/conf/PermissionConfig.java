package com.gstsgy.permission.conf;

import com.gstsgy.permission.filter.JwtRequestFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionConfig {

    @Bean
    // 可关闭jwt 权限校验
    @ConditionalOnExpression("${gstsgy.permission.jwt.open:${gstsgy.permission.jwt.open:true}}")
    public JwtRequestFilter jwtRequestFilter(){
        return new JwtRequestFilter();
    }
}
