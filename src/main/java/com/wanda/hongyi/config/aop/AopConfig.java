package com.wanda.hongyi.config.aop;

import com.wanda.hongyi.config.jjwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/26 16:29
 */
@Aspect
@Component
@Slf4j
public class AopConfig {
    @Pointcut("@annotation(com.wanda.hongyi.anno.TokenCheck)")
    public void tokenCheck() {
    }

    ;

    @Before("tokenCheck()")
    public void before() throws ServletException {
        //获取 token
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("invalid Authorization header");
        }
        //取得token
        String token = authHeader.substring(7);
        try {
            JwtUtil.checkToken(token);
            log.info("token:" + token);
//            return true;
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}
