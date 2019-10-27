package com.wanda.hongyi.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/27 15:45
 */
@Controller
@RequestMapping(value = "/hongyi/permissioncheck")
public class PermissionCheckController {

    @RequestMapping(value = "/tokencheck",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> tokenCheck(){
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success",true);
        return modelMap;
    }
}
