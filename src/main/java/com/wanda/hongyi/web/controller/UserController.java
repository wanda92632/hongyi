package com.wanda.hongyi.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanda.hongyi.config.jjwt.JwtUtil;
import com.wanda.hongyi.config.shiro.PasswordHelper;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.service.UserService;
import com.wanda.hongyi.util.IPUtil;
import com.wanda.hongyi.util.TokenUtil;
import com.wanda.hongyi.vo.UserOperation;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-10-20
 */
@RestController
@RequestMapping("/hongyi/admin")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**获取用户列表每页大小**/
    private static long SIZE = 10;

    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> userLogin(@RequestBody User loginInfo, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取登录信息
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        UsernamePasswordToken authenticationToken = new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(authenticationToken);
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

        //获取 token
        String token = JwtUtil.getToken(username);

        //获取 ip 地址
        String ipAddress = IPUtil.getIpAddress(request);

        //缓存用户信息,实现单点登录
        stringRedisTemplate.opsForValue().set(ipAddress,token , 1000 * 60 * 60, TimeUnit.MILLISECONDS);

        modelMap.put("success", true);
        log.info("登录成功 登录名:{} 密码:{} ip地址:{}", username, password,ipAddress);

        //返回 Token
        modelMap.put("token", token);

        //更新用户最后登录时间
        User user = (User) subject.getPrincipal();
        user.setLastVisitTime(LocalDateTime.now());
        userService.updateById(user);

        return modelMap;
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public void userRegister(@RequestBody User user){
        passwordHelper.encryptPassword(user);
        //设置用户状态
        user.setStatus(0);
        userService.save(user);
        log.info("用户注册：用户名：{} 密码：{}",user.getUsername(),user.getPassword());
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Map<String, Object> logout(HttpServletRequest request) throws ServletException {
        Map<String, Object> modelMap = new HashMap<>();

        //获取 token
        String token = TokenUtil.getToken(request);
        //获取用户名
        String username = JwtUtil.getUsername(token);

        //清除缓存
        stringRedisTemplate.delete(IPUtil.getIpAddress(request));

        modelMap.put("success", true);
        log.info("用户退出:" + username);

        return modelMap;
    }

    /**
     * 获取用户列表
     *
     * @param current
     * @return
     */
    @RequestMapping(value = "/getalluser", method = RequestMethod.POST)
    public Map<String, Object> getAllUser(@RequestBody Integer current) {
        Map<String, Object> modelMap = new HashMap<>();
        // TODO 权限认证
        List<User> userList = userService.page(new Page<>(current, SIZE)).getRecords();
        String userListJson = JSONObject.toJSONString(userList);
        modelMap.put("success", true);
        modelMap.put("userList", userListJson);
        return modelMap;
    }

    /**
     * 修改用户状态用户
     *
     * @param userOperationu
     * @return
     */
    @RequestMapping(value = "/disableduserstatus", method = RequestMethod.POST)
    public Map<String, Object> modifyUserStatus(@RequestBody UserOperation userOperationu) {
        Map<String, Object> modelMap = new HashMap<>();
        // TODO 权限认证
        log.info("禁用用户" + userOperationu.getUserId());
        User user = userService.getById(userOperationu.getUserId());
        if (user != null) {
            Integer status = userOperationu.getOperationCode() == 0 ? 0 : 1;
            user.setStatus(status);
            userService.updateById(user);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户不存在");
        }
        return modelMap;
    }


    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "updateuserdata",method = RequestMethod.POST)
    public void updateUserData(@RequestBody User user) {
        // TODO 权限鉴定
        userService.updateById(user);
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @RequestMapping(value = "removeuser",method = RequestMethod.POST)
    public void removeUser(@RequestBody long  id){
        // TODO 权限鉴定
        userService.removeById(id);
    }

    /**
     * 批量删除用户
     *
     * @param id
     */
    @RequestMapping(value = "batchremoveuser",method = RequestMethod.POST)
    public void batchRemoveUser(@RequestBody List<Long>  id){
        // TODO 权限鉴定
        userService.removeByIds(id);
    }
}
