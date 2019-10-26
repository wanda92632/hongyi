package com.wanda.hongyi.config.shiro;

import com.wanda.hongyi.entity.Permission;
import com.wanda.hongyi.entity.Role;
import com.wanda.hongyi.entity.User;
import com.wanda.hongyi.service.PermissionService;
import com.wanda.hongyi.service.RoleService;
import com.wanda.hongyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/21 10:26
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取当前用户
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 从数据库或者缓存中获取角色数据
        List<Role> roleList = roleService.findUserRoleByUsername(user.getId());
        for (Role role : roleList) {
            authorizationInfo.addRole(role.getRoleName());
        }
        // 从数据库或者缓存中获取权限数据
        Set<Permission> permissions = permissionService.findListByRole(roleList);
        Set<String> permissionNameSet = permissions.stream().map(Permission::getPermissionName).collect(Collectors.toSet());
        authorizationInfo.setStringPermissions(permissionNameSet);
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("用户登录");
        User user = null;
        //获取登录名
        String account = (String) token.getPrincipal();
        //查询用户信息
        user = userService.findByUsername(account);
        if (user == null) {
            //用户不存在
            log.info("登录失败，用户不存在！ 登录名：{}，密码：{}", account, token.getCredentials());
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
    }
}
