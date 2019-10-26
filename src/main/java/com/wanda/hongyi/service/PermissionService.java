package com.wanda.hongyi.service;

import com.wanda.hongyi.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanda.hongyi.entity.Role;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/20 23:33
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据角色列表查询权限集合
     *
     * @param roleList
     * @return
     */
    Set<Permission> findListByRole(List<Role> roleList);
}
