package com.wanda.hongyi.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handleException(Exception e) {
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        map.put("errMsg",e.getMessage());
        return map;
    }
}
