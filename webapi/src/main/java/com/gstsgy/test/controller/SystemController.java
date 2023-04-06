package com.gstsgy.test.controller;

import com.gstsgy.base.model.ResponseBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "系统接口")
@RestController
public class SystemController {

    @Operation(description = "banner")
    @GetMapping("/")
    public ResponseBean banner(){
        return ResponseBean.getSuccess("workers-help后端接口");
    }
    @Operation(description = "版本")
    @GetMapping({"/version","/v"})
    public ResponseBean version(){
        return ResponseBean.getSuccess("2023.01.30");
    }
}
