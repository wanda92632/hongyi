package com.wanda.hongyi.config.jjwt;

import com.wanda.hongyi.util.IPUtil;
import com.wanda.hongyi.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/23 16:55
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //取得token
        String token = TokenUtil.getToken(request);

        try {
            //验证 token 有效性
            JwtUtil.checkToken(token);
            //验证 登录有效性，实现禁止多点登录
            String ipAddress = IPUtil.getIpAddress(request);
            if(Objects.equals(stringRedisTemplate.hasKey(ipAddress),true)){
                //存在 token 进行校验
                String redisToken = stringRedisTemplate.opsForValue().get(ipAddress);
                if(!Objects.equals(redisToken,token)){
                    log.info("登录无效，已在其他地方登录");
                    //判断是否为同一 token
                    throw new Exception("登录无效，已在其他地方登录");
                }
            }else {
                throw new Exception("无效登录，请重新登录");
            }
            return true;
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}