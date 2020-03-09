package kim.nzxy.auth.common.auth;

import kim.nzxy.auth.models.system.permission.PermissionService;
import kim.nzxy.auth.models.system.role.Role;
import kim.nzxy.auth.models.system.role.RoleService;
import kim.nzxy.auth.models.system.token.SysToken;
import kim.nzxy.auth.models.system.token.SysTokenService;
import kim.nzxy.auth.models.system.user.User;
import kim.nzxy.auth.models.system.user.UserService;
import kim.nzxy.auth.common.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xy
 */
@Component
@AllArgsConstructor
public class AuthRealm extends AuthorizingRealm {

    private final UserService       userService;
    private final SysTokenService   tokenService;
    private final RoleService       roleService;
    private final PermissionService permissionService;
    /**
     * 认证(登陆时候调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo (AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        //1. 根据accessToken，查询用户信息
        SysToken tokenEntity = tokenService.findByToken(accessToken);
        if (tokenEntity == null || LocalDateTimeUtil.getMilliByTime(
                tokenEntity.getExpireTime()) < System.currentTimeMillis()) {
            System.out.println("登录失败");
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        val user = userService.findById(tokenEntity.getUserId());
        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在!");
        }
        //5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        return new SimpleAuthenticationInfo(user, accessToken, this.getName());
    }
    /**
     * 授权(验证权限时候调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo (PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        User userInfo = (User) principals.getPrimaryPrincipal();
        //2.添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<Role>               roleList                = roleService.findByUserId(userInfo.getId());
        Set<String>             portList                = permissionService.getPortByUserId(userInfo.getId());
        for (Role role : roleList) {
            //2.1添加角色
            simpleAuthorizationInfo.addRole(role.getName());
        }
        for (String port : portList) {
            simpleAuthorizationInfo.addStringPermission(port);
        }
        return simpleAuthorizationInfo;
    }

}
