package com.wanda.hongyi.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/21 14:55
 */
public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static Long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1L;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1D;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String key) {
        try{
            String result = request.getParameter(key);
            result=result.trim();
            if(Objects.equals(request,"")){
                return null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
