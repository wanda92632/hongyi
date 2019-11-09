package com.wanda.hongyi.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/23 16:56
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServletException.class)
    public Map<String,Object> handleException(Exception e) {
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        log.info("-----------异常开始----------");
        log.info(e.getMessage());
        map.put("errMsg",e.toString());
        log.info("-----------异常结束----------");
        return map;
    }
}
