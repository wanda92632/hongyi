package com.wanda.hongyi;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.mapper.UserMapper;
import com.wanda.hongyi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HongyiApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Test
    public void contextLoads() {
        List<User> userList = userService.page(new Page<>(1,2)).getRecords();
        System.out.println(userList.size());
        for (User user:userList){
            System.out.println(user);
        }
    }

}
