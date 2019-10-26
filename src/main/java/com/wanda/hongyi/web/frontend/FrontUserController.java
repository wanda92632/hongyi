package com.wanda.hongyi.web.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/21 15:46
 */
@Controller
@RequestMapping("/hongyi/user")
@Slf4j
public class FrontUserController {



    @RequestMapping(value = "/registerpage")
    public String registerPage(){
        return "admin/register";
    }
}
