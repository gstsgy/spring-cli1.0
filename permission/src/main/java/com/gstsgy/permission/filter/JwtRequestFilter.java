package com.gstsgy.permission.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.utils.AuthWhiteUtil;
import com.gstsgy.permission.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

       // HttpServletResponse res = response;
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            ResponseBean responseBean = ResponseBean.getSuccess(true);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
            return;
        }



        if(!JWTUtil.isOpen){
            chain.doFilter(request, response);
            return;
        }

        String url = request.getRequestURI();
        String token = request.getHeader("token");
        if (AuthWhiteUtil.matches(url)) {
            chain.doFilter(request, response);
            return;
        }
        if (!StringUtils.hasText(token)) {

            ResponseBean responseBean = ResponseBean.getNoLogin("Token不存在");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
            return;
        }
        try {
            JWTUtil.validateToken(token);
            chain.doFilter(request, response);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ResponseBean responseBean = ResponseBean.getNoLogin("Invalid Token");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
        }
    }
}



