package com.gstsgy.test.enable;



import com.gstsgy.permission.utils.AuthWhiteUtil;
import com.gstsgy.permission.utils.JWTUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AuthOpenUrlEnable implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        AuthWhiteUtil.addUrl("/");
        AuthWhiteUtil.addUrl("/v");
        AuthWhiteUtil.addUrl("/version");
        AuthWhiteUtil.addUrl("/test/\\w*");
        AuthWhiteUtil.addUrl("/con/\\w*");
        AuthWhiteUtil.addUrl("/swagger-ui/.*");
        AuthWhiteUtil.addUrl("/v3/.*");
        AuthWhiteUtil.addUrl("/erp/.*");
        JWTUtil.timeout = 1000 * 60*60*12*15;

    }
}
