package com.wanda.hongyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanda.hongyi.entity.Role;
import com.wanda.hongyi.mapper.RoleMapper;
import com.wanda.hongyi.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/20 23:33
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 根据用户名获得用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findUserRoleByUsername(Long userId) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return roleMapper.selectList(queryWrapper);
    }
}
