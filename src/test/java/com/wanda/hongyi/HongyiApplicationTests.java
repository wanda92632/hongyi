package com.wanda.hongyi;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanda.hongyi.config.jjwt.JwtUtil;
import com.wanda.hongyi.config.shiro.PasswordHelper;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.mapper.UserMapper;
import com.wanda.hongyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HongyiApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private PasswordHelper passwordHelper;


    @Test
    public void contextLoads() {
        //用户名，加密字段
        String username = "zhifeizhifei";
        //生成 token
        String token = JwtUtil.getToken(username);
        template.opsForValue().set(token,username);
        template.opsForValue().set(username,token);
        long startTime1 = System.currentTimeMillis();
        String redis=template.opsForValue().get(token);
        long endTime1 = System.currentTimeMillis();
        log.info("token为 key {},用时:{}",redis,endTime1-startTime1);

        long startTime2 = System.currentTimeMillis();
        String redis2=template.opsForValue().get(token);
        long endTime2 = System.currentTimeMillis();
        log.info("username 为 key {},用时:{}",redis,endTime2-startTime2);

//        //存入redis
//        template.opsForValue().set(token,username);
//        log.info("username:{} token:{}",username,token);
//
//        //token 解析
//        long startTime = System.currentTimeMillis();
//        String jiexi = JwtUtil.getUsername(token);
//        long endTime = System.currentTimeMillis();
//        log.info("解析 username {},用时:{}",jiexi,endTime-startTime);
//
//
//
//        //从 redis 取
//        long startTime2 = System.currentTimeMillis();
//        String redis=template.opsForValue().get(token);
//        long endTime2 = System.currentTimeMillis();
//        log.info("redis username {},用时:{}",redis,endTime2-startTime2);
    }

    @Test
    public void test() throws Exception {
        User user = new User();
        for(long i=100;i<200;i++){
            user.setUsername(String.valueOf(i));
            user.setPassword("123123");
            user.setNickname("红衣");
            user.setSex("男");
            user.setPhone("1234567890");
            user.setEmail("asdasd@asdas.com");
            user.setCreateTime(LocalDateTime.now());
            user.setStatus(0);
            passwordHelper.encryptPassword(user);
            userService.save(user);
        }
    }
}
