package com.gstsgy.export.service.impl;

import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.export.bean.ExportTemplate;
import com.gstsgy.export.bean.ParameterRequestWrapper;
import com.gstsgy.export.bean.WebColumn;
import com.gstsgy.export.function.MethodGetFunction;
import com.gstsgy.export.service.ExportDataService;
import com.gstsgy.tools.excel.WriteExcel;
import com.gstsgy.tools.excel.bean.OrderFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.pattern.PathPattern;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/1/10 下午3:05
 */
@Service
//@Transactional
public class ExportDataServiceImpl implements ExportDataService {


    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Override
    public void export(ExportTemplate template, HttpServletRequest request, HttpServletResponse response) throws Exception {
        template.getParams().put("pageNum", 1);
        template.getParams().put("pageSize", -1);
        HandlerMethod handlerMethod = getHandlerMethod(template);
        List<Object> data = template.getData();
        if (data == null || data.size() == 0) {
            Object responseBean = request(template, handlerMethod, request, response);
            data = ExcportConfUtils.parseResponseFun.parse(responseBean);
        }
        ExcportConfUtils.getFunction.controllerType = handlerMethod.getBeanType();
        writeExcel(template, data, response);
    }

    public HandlerMethod getHandlerMethod(ExportTemplate template) {
        Map<RequestMappingInfo, HandlerMethod> maps = handlerMapping.getHandlerMethods();
        for (RequestMappingInfo requestMappingInfo : maps.keySet()) {
            if (requestMappingInfo.getPathPatternsCondition() != null) {
                Set<String> patterns = requestMappingInfo.getPathPatternsCondition().getPatterns().stream().map(PathPattern::getPatternString).collect(Collectors.toSet());
                if (patterns.contains(template.getUrl()) && requestMappingInfo.getMethodsCondition().getMethods().contains(template.getMethod())) {
                    return maps.get(requestMappingInfo);
                }
            }
        }
        return null;
    }


    public Object request(ExportTemplate template, HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Object[] params = resolveArgument(request, response, handlerMethod, template);
        Object controller = SpringUtils.getBean((String) handlerMethod.getBean());
        return handlerMethod.getMethod().invoke(controller,
                params);
    }


    public void writeExcel(ExportTemplate template, List sourceData, HttpServletResponse response) throws IOException, CloneNotSupportedException {

        List<WebColumn> dataTemplates = template.getColumns();
        dataTemplates = dataTemplates.stream().filter(WebColumn::isShow).
                filter(it -> !Objects.equals(it.getKey(), "select")).collect(Collectors.toList());
        dataTemplates.sort(Comparator.comparing(WebColumn::getSeq));
        // 拿到列头
        List<String> tiele = dataTemplates.stream().map(WebColumn::getTitle).collect(Collectors.toList());

        response.setContentType("application/x-download");
        String fileName = String.format("export-%s.xlsx", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        fileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8);

        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName); // 设置文件名

        WriteExcel.WriteExcelBuild<Object> writeExcelBuild = WriteExcel.build();
        writeExcelBuild = writeExcelBuild.
                setOutputStream(response.getOutputStream()).
                setTitles(tiele);
        if (sourceData.size() > 0) {
            Class type = sourceData.get(0).getClass();
            ExcportConfUtils.getFunction.targetType = type;
            for (WebColumn webColumn : dataTemplates) {
                if (Objects.equals(webColumn.getKey(), "index")) {
                    writeExcelBuild = writeExcelBuild.addGetMethos(new OrderFunction());
                    continue;
                }

                MethodGetFunction methodGetFunction = ExcportConfUtils.getFunction.clone();
                methodGetFunction.fieldName = webColumn.getKey();
                writeExcelBuild = writeExcelBuild.addGetMethos(methodGetFunction);
            }
        }


        WriteExcel<Object> writeExcel = writeExcelBuild.create();
        writeExcel.writeToNewSheet(sourceData);
        writeExcel.save();
    }


    private Object[] resolveArgument(HttpServletRequest request1, HttpServletResponse response, HandlerMethod method, ExportTemplate tmp) throws Exception {
        ParameterRequestWrapper request = new ParameterRequestWrapper(request1);
        request.addAllParameters(tmp.getParams());
        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        modelAndViewContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
        MethodParameter[] methodParameters = method.getMethodParameters();
        Object[] args = new Object[methodParameters.length];
        for (int i = 0; i < methodParameters.length; i++) {
            args[i] = parseParam(methodParameters[i], webRequest, method, modelAndViewContainer);
        }
        return args;
    }

    private Object parseParam(MethodParameter parameter, ServletWebRequest webRequest, HandlerMethod method, ModelAndViewContainer mav) throws Exception {
        List<HandlerMethodArgumentResolver> argumentResolvers = handlerAdapter.getArgumentResolvers();

        Method method1 = RequestMappingHandlerAdapter.class.getDeclaredMethod("getDataBinderFactory", HandlerMethod.class);
        parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
        method1.setAccessible(true);
        WebDataBinderFactory binderFactory = (WebDataBinderFactory) method1.invoke(handlerAdapter, new Object[]{method});

        assert argumentResolvers != null;
        for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
            if (argumentResolver.supportsParameter(parameter)) {
                return argumentResolver.resolveArgument(parameter, mav, webRequest, binderFactory);
            }
        }
        return null;
    }
}
