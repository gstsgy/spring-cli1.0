package com.gstsgy.message.websocket.conf;

/**
 * @ClassName MyInterceptor
 * @Description TODO
 * @Author guyue
 * @Date 2020/9/9 下午3:31
 **/

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
 class MyInterceptor implements HandshakeInterceptor {

/**
     * 握手前
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception*/


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //System.out.println("握手开始");
        HashMap map = decodeParam(request.getURI().getQuery());
        if(map==null){
            return false;
        }
        String authorization =String.valueOf(map.get("token")) ;
        if (authorization!=null&&!authorization.equals("")) {

          // Long userId = JWTUtil.getUsername(authorization);
            // 放入属性域
            attributes.put("user", authorization);
           // System.out.println("用户 token " + authorization + " 握手成功！");
            return true;
        }
        //System.out.println("用户登录已失效");
        return false;
    }

/**
     * 握手后
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param exception*/


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
      //  System.out.println("握手完成");
    }

    private HashMap decodeParam(String sourceStr){
        if(sourceStr==null){
            return null;
        }
        HashMap res = new HashMap();
        String [] tmpstr = sourceStr.split("&");
        for(String item:tmpstr){
            String[] tmp = item.split("=");
            res.put(tmp[0],tmp[1]);
        }
        return res;
    }
}
