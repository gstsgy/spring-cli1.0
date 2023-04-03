package com.gstsgy.export.service;

import com.gstsgy.export.bean.ExportTemplate;
import com.gstsgy.export.function.MethodGetFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/1/10 下午3:01
 */
public interface ExportDataService {
    void export(ExportTemplate template, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
