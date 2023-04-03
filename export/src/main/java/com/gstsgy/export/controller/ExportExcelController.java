package com.gstsgy.export.controller;


import com.gstsgy.export.bean.ExportTemplate;
import com.gstsgy.export.service.ExportDataService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/1/7 下午3:32
 */
/*@Component*/
@RestController
@RequestMapping("export")
public class ExportExcelController {

    @Autowired
    public ExportDataService exportDataService;

    @PostMapping("excel")
    public void export(@RequestBody ExportTemplate template, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //container.put(template.getName(), template.getColumns());
        //return ResponseBean.getSuccess(true);
        exportDataService.export(template,request,response);
    }
}
