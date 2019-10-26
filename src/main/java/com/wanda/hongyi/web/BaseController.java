package com.wanda.hongyi.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/21 16:52
 */
@Controller
@RequestMapping("/hongyi/frontend")
@Slf4j
public class BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin-add")
    public String adminAdd(){
        return "admin/admin-add";
    }

    @RequestMapping(value = "/admin-cate")
    public String adminCate(){
        return "admin/admin-cate";
    }

    @RequestMapping(value = "/admin-edit")
    public String adminEdit(){
        return "admin/admin-edit";
    }
    @RequestMapping(value = "/admin-list")
    public String adminList(){
        return "admin/admin-list";
    }
    @RequestMapping(value = "/admin-role")
    public String adminRole(){
        return "admin/admin-role";
    }

    @RequestMapping(value = "/admin-rule")
    public String adminRule(){
        return "admin/admin-rule";
    }

    @RequestMapping(value = "/echarts1")
    public String echarts1(){
        return "admin/echarts1";
    }

    @RequestMapping(value = "/echarts2")
    public String echarts2(){
        return "admin/echarts2";
    }

    @RequestMapping(value = "/echarts3")
    public String echarts3(){
        return "admin/echarts3";
    }

    @RequestMapping(value = "/echarts4")
    public String echarts4(){
        return "admin/echarts4";
    }

    @RequestMapping(value = "/echarts5")
    public String echarts5(){
        return "admin/echarts5";
    }

    @RequestMapping(value = "/echarts6")
    public String echarts6(){
        return "admin/echarts6";
    }

    @RequestMapping(value = "/echarts7")
    public String echarts7(){
        return "admin/echarts7";
    }

    @RequestMapping(value = "/echarts8")
    public String echarts8(){
        return "admin/echarts8";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "admin/index";
    }

    /*** 登录页面**/
    @RequestMapping(value = "/loginpage")
    public String loginPage(){
        return "admin/login";
    }

    @RequestMapping(value = "/member-add")
    public String memberAdd(){
        return "admin/member-add";
    }

    @RequestMapping(value = "/member-del")
    public String memberDel(){
        return "admin/member-del";
    }

    @RequestMapping(value = "/member-edit")
    public String memberEdit(){
        return "admin/member-edit";
    }

    /**
     * 用户列表页面
     *
     * @return
     */
    @RequestMapping(value = "/member-list/{pageNum}")
    public String memberList(Model model, @PathVariable Integer pageNum){
        List<User> userList = userService.page(new Page<User>(pageNum,10)).getRecords();
        model.addAttribute("userList",userList);
        return "admin/member-list";
    }

    @RequestMapping(value = "/member-password")
    public String memberPassword(){
        return "admin/member-password";
    }

    @RequestMapping(value = "/order-add")
    public String orderAdd(){
        return "admin/order-add";
    }

    @RequestMapping(value = "/order-list")
    public String orderList(){
        return "admin/order-list";
    }

    @RequestMapping(value = "/role-add")
    public String roleAdd(){
        return "admin/role-add";
    }

    @RequestMapping(value = "/welcome")
    public String welcome(){
        return "admin/welcome";
    }

}
