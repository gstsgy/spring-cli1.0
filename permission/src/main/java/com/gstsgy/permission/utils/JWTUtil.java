package com.gstsgy.permission.utils;

import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.exception.TokenExpetion;
import com.gstsgy.permission.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

public class JWTUtil {
    public static boolean isOpen = true;
    /**
     * 密钥15天过期
     */
    public static Integer timeout = 1000 * 60 * 60 * 24 * 15;


    public static SecretKey generalKeyByDecoders() {

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ="));

    }


    public static String createJwtToken(OperatorDO user) {

        //创建token,在token中加入必备信息
        return Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getPasswd())
                .signWith(generalKeyByDecoders())
                .setIssuedAt(new Date()) //设置创建时间戳
                .setExpiration(new Date(System.currentTimeMillis() + timeout))
                .compact();

    }

    private static UserService userService;

    /**
     * 验签token的方法
     */
    public static void validateToken(String jwtToken) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(generalKeyByDecoders())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        //解析出用户的自定义信息
        OperatorDO user = new OperatorDO();
        user.setPasswd(claim.getSubject());
        user.setId(Long.parseLong(claim.getId()));
        if (userService == null) {
            userService = SpringUtils.getBean(UserService.class);
        }
        OperatorDO tmp = userService.queryOne(user.getId());
        if (!Objects.equals(tmp.getPasswd(), user.getPasswd())) {
            throw new TokenExpetion("密码以变更");
        }
        setUser(user.getId());
    }


    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiMTExMTExIiwiaWF0IjoxNjc1MjM3NDkwLCJleHAiOjE2NzY1MzM0OTB9.Re2k4oXdq58LaDKnGdNh4-nDUmmyzWTxk8pqqYXKOi8";
        validateToken(token);
        System.out.println(token);
    }

    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();

    public static Long getUser() {
        return userId.get();
    }

    public static void setUser(Long userId) {
        JWTUtil.userId.set(userId);
    }
}
