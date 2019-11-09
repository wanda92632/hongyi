package com.wanda.hongyi.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/21 14:55
 */
public class TokenUtil {
    public static String getToken(HttpServletRequest request) throws ServletException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("invalid Authorization header");
        }
        //返回token
        return authHeader.substring(7);
    }
}
