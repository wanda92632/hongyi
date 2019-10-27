package com.wanda.hongyi.web.controller;


import com.wanda.hongyi.anno.TokenCheck;
import com.wanda.hongyi.config.jjwt.JwtUtil;
import com.wanda.hongyi.config.shiro.PasswordHelper;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.service.UserService;
import com.wanda.hongyi.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-10-20
 */
@Controller
@RequestMapping("/hongyi/admin")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;
    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userLogin(@RequestBody User loginInfo) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取登录信息
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            log.info("登录失败-密码错误：登录名：{}，密码：{}", username, password);
            modelMap.put("success", false);
            modelMap.put("errMsg", "密码错误");
            return modelMap;
        } catch (UnknownAccountException uae) {
            log.info("登录失败-用户名不存在：登录名：{}，密码：{}", username, password);
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户不存在");
            return modelMap;
        }
        // 登录成功

        modelMap.put("success", true);
        log.info("登录成功 登录名：{}，密码：{}", username, password);

        //写入 Token
        modelMap.put("token", JwtUtil.getToken(username));

        //更新用户最后登录时间
        User user = (User) subject.getPrincipal();
        userService.updateById(user);

        return modelMap;
    }

//    /**
//     * 用户注册
//     *
//     * @param request
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/register",method = RequestMethod.POST)
//    public String userRegister(HttpServletRequest request,Model model){
//        User user = new User();
//        user.setUsername(HttpServletRequestUtil.getString(request,"username"));
//        user.setPassword(HttpServletRequestUtil.getString(request,"password"));
//        passwordHelper.encryptPassword(user);
//        //设置用户状态
//        user.setStatus(0);
//        userService.save(user);
//        log.info("用户注册：用户名：{} 密码：{}",user.getUsername(),user.getPassword());
//        return "redirect:admin/index";
//    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success",true);
        log.info("用户退出登录");
        return modelMap;
    }
}
