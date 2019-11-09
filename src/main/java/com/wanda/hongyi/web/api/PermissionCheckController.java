package com.wanda.hongyi.web.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/27 15:45
 */
@RestController
@RequestMapping(value = "/hongyi/api")
@Slf4j
public class PermissionCheckController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/tokencheck",method = RequestMethod.POST)
    public Map<String,Object> tokenCheck(){
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success",true);
        return modelMap;
    }

    /**
     * 获取所有用户信息
     *
     * @return
     */
    @RequestMapping(value = "/getalluser",method = RequestMethod.GET)
    public Map<String,Object> getAllUser(){
        int current=1;
        Map<String,Object> modelMap = new HashMap<>();
        // TODO 权限认证
        List<User> userList = userService.page(new Page<>(current,2)).getRecords();
        String userListJson = JSONObject.toJSONString(userList);
        System.out.println(userList);
        log.info(userListJson);
        modelMap.put("success",true);
        modelMap.put("userList",userListJson);
        return modelMap;
    }

    /**
     * 获取用户总数
     *
     * @return
     */
    @RequestMapping(value = "/getusercount",method = RequestMethod.GET)
    public Map<String,Object> getUserCount(){
        Map<String,Object> modelMap = new HashMap<>();
        int count = userService.count();
        modelMap.put("success",true);
        modelMap.put("count",count);
        return modelMap;
    }
}
