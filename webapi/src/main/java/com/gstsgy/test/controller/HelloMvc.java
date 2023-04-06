package com.gstsgy.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class HelloMvc {
    @Autowired
    ApplicationContext applicationContext;
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "123";
    }
}
