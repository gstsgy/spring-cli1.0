package com.gstsgy.test.controller;

import com.gstsgy.workers.help.bean.ParameterRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/con")
public class ConTest {
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;
    @GetMapping("t1")
    public void t1(HttpServletRequest request1, HttpServletResponse response,int id) throws Exception {
        ParameterRequestWrapper request = new ParameterRequestWrapper(request1);
        request.addParameter("id",id);
        HandlerMethod  method= getRequestMappingInfo("/test/t1",RequestMethod.GET);
        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        modelAndViewContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
        MethodParameter[] methodParameters = method.getMethodParameters();
        for(MethodParameter parameter:methodParameters){
            Object obj = parseParam(parameter,webRequest,method,modelAndViewContainer);
            System.out.println(obj);
        }
        //System.out.println(22222);
    }
    public HandlerMethod getRequestMappingInfo(String url, RequestMethod requestMethod){
        Map<RequestMappingInfo, HandlerMethod> maps = handlerMapping.getHandlerMethods();
        for(RequestMappingInfo requestMappingInfo:maps.keySet()){
            if(requestMappingInfo.getPathPatternsCondition() != null){
                Set<String> patterns= requestMappingInfo.getPathPatternsCondition().getPatterns().stream().map(PathPattern::getPatternString).collect(Collectors.toSet());
                if(patterns.contains(url)&&requestMappingInfo.getMethodsCondition().getMethods().contains(requestMethod)){
                    return maps.get(requestMappingInfo);
                }

            }
        }
        return null;
    }

    public Object parseParam(MethodParameter parameter,ServletWebRequest webRequest,HandlerMethod method,ModelAndViewContainer mav) throws Exception {
        List<HandlerMethodArgumentResolver> argumentResolvers= handlerAdapter.getArgumentResolvers();

        Method method1 = RequestMappingHandlerAdapter.class.getDeclaredMethod("getDataBinderFactory",HandlerMethod.class);

        method1.setAccessible(true);
        WebDataBinderFactory binderFactory = (WebDataBinderFactory) method1.invoke(handlerAdapter,new Object[]{method});

        assert argumentResolvers != null;
        for(HandlerMethodArgumentResolver argumentResolver:argumentResolvers){
            if(argumentResolver.supportsParameter(parameter)){
                return argumentResolver.resolveArgument(parameter,mav,webRequest,binderFactory);
            }
        }
        return null;
    }
}
