package com.wanda.hongyi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanda.hongyi.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/20 23:33
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据用户名匹配用户
     *
     * @param str
     * @return
     */
    List<User> findByStr(String str);
}
