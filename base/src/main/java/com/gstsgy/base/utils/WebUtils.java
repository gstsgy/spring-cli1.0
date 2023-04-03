package com.gstsgy.base.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.ref.SoftReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/2/17 下午1:16
 */
public class WebUtils {
    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    private static SoftReference<Map<String, Locale>> sessionLocale = new SoftReference<>(new ConcurrentHashMap<>());

    public static void setLocale(HttpServletRequest request, Locale locale) {
        String token = request.getHeader("token");
        if (token == null) {
            token = "1";
        }
        if (sessionLocale.get() == null) {
            sessionLocale = new SoftReference<>(new ConcurrentHashMap<>());
        }
        sessionLocale.get().put(token, locale);
    }



    public static Locale getLocale(HttpServletRequest request) {
        if (sessionLocale.get() == null) {
            sessionLocale = new SoftReference<>(new ConcurrentHashMap<>());
        }
        String token = "1";
        if (request != null) {
            token = request.getHeader("token");
        }
        if (token == null) {
            token = "1";
        }
        if (Objects.requireNonNull(sessionLocale.get()).get(token) != null) {
            return Objects.requireNonNull(sessionLocale.get()).get(token);
        }


        return Locale.getDefault();
    }



    public static Locale getLocale() {

        return getLocale(getHttpServletRequest());
    }



    /**
     * 得到当前线程的请求对象
     *
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {

        return getServletRequestAttributes()==null?null:getServletRequestAttributes().getRequest();
    }

    public static void setHttpServletRequest(HttpServletRequest request) {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    /**
     * 得到访问的ip地址
     *
     * @return
     */
    public static String getRequestIp() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = null;
        assert request != null;
        ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert inet != null;
                ip = inet.getHostAddress();
            }
        }
        if ((ip != null) && (ip.length() > 15)) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 得到当前线程的响应对象
     */
    public static HttpServletResponse getHttpServletResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 得到session对象
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    /**
     * 得到servletContext对象
     */
    public static ServletContext getServletContext() {
        return getHttpServletRequest().getServletContext();
    }
}
