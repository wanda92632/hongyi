package com.wanda.hongyi.service;

import com.wanda.hongyi.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/20 23:33
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户名获得用户的角色
     *
     * @param userId
     * @return
     */
    List<Role> findUserRoleByUsername(Long userId);
}
