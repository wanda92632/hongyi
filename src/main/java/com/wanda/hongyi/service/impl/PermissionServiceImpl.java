package com.wanda.hongyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanda.hongyi.entity.Permission;
import com.wanda.hongyi.entity.Role;
import com.wanda.hongyi.entity.RolePermissionRef;
import com.wanda.hongyi.mapper.PermissionMapper;
import com.wanda.hongyi.mapper.RolePermissionRefMapper;
import com.wanda.hongyi.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/20 23:33
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionRefMapper rolePermissionRefMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    /**
     * 根据角色列表查询权限集合
     * TODO  Redis 以角色列表为key进行缓存
     *
     * @param roleList
     * @return
     */
    @Override
    public Set<Permission> findListByRole(List<Role> roleList) {
        //1.获取角色 id 列表
        List<Integer> roleIdList=roleList.stream().map(Role::getId).collect(Collectors.toList());

        //2.根据权限 id 列表 获取多对多关系
        List<RolePermissionRef> rolePerwmissionRefList = rolePermissionRefMapper.selectBatchIds(roleIdList);

        //3.获取权限 id 列表
        List<Integer> permissionIdList = rolePerwmissionRefList.stream().map(RolePermissionRef::getPermissionId).collect(Collectors.toList());

        //4.根据对应的关系 获取相应的权限实体列表
        Set<Permission> permissionSet = new HashSet<>(permissionMapper.selectBatchIds(permissionIdList));
        return permissionSet;
    }
}
