package com.wanda.hongyi.config.jjwt;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.util.Date;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/23 16:51
 */
@Slf4j
public class JwtUtil {
    /**
     * 私钥
     **/
    private final static String BASE64_ENCODED_SECRET_KEY = "BASE64_ENCODED_SECRET_KEY";
    /**
     * 过期时间,测试使用60秒
     **/
    private final static long TOKEN_EXP = 1000 * 60*60;

    /**
     * 获取 Token
     *
     * @param userName
     * @return
     */
    public static String getToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP))
                .signWith(SignatureAlgorithm.HS256, BASE64_ENCODED_SECRET_KEY)
                .compact();

    }

    /**
     * 获取登录名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
            Claims claims = Jwts.parser().setSigningKey(BASE64_ENCODED_SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getSubject();
    }

    /**
     * 检查token,只要不正确就会抛出异常
     *
     * @param token
     * @throws ServletException
     */
    public static void checkToken(String token) throws ServletException {
        try {
            final Claims claims = Jwts.parser().setSigningKey(BASE64_ENCODED_SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e1) {
            throw new ServletException("token 已过期");
        } catch (Exception e) {
            throw new ServletException("token 校验失败");
        }
    }
}
