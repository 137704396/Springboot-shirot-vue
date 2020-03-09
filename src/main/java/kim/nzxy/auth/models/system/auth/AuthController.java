package kim.nzxy.auth.models.system.auth;

import kim.nzxy.auth.common.entity.Result;
import kim.nzxy.auth.common.enums.ResultCodeEnum;
import kim.nzxy.auth.models.system.permission.PermissionService;
import kim.nzxy.auth.models.system.role.RoleService;
import kim.nzxy.auth.models.system.router.RouterService;
import kim.nzxy.auth.models.system.user.User;
import kim.nzxy.auth.models.system.user.UserService;
import kim.nzxy.auth.common.util.AuthUtils;
import kim.nzxy.auth.common.util.FindTree;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xy
 */
@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private final UserService       userService;
    private final RouterService     routerService;
    private final RoleService       roleService;
    private final PermissionService permissionService;
    /**
     * 登录
     */
    @PostMapping("login")
    public Result login (String username, String password) {
        // 参数无效
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        //用户信息
        val user = userService.findByUsername(username);
        // 用户不存在
        if (user == null) {
            return Result.failure(ResultCodeEnum.USER_NOT_EXIST);
        }
        if (! user.getEnable()) {
            return Result.failure(ResultCodeEnum.USER_ACCOUNT_FORBIDDEN);
        }
        System.out.println("password = " + password);
        System.out.println("user = " + user);
        //账号不存在、密码错误
        if (! AuthUtils.testPassword(password, user.getPassword())) {
            return Result.failure(ResultCodeEnum.USER_PASSWORD_ERR);
        }
        //生成token，并保存到数据库
        return Result.success(userService.createToken(user.getId()));
    }

    /**
     * 登出
     */
    @PostMapping("logout")
    public Result logout () {
        User user = (User) SecurityUtils.getSubject()
                .getPrincipal();
        userService.logout(user.getId());
        return Result.success();
    }

    /**
     * 用户信息
     */
    @GetMapping("info")
    public Result info () {
        User user = (User) SecurityUtils.getSubject()
                .getPrincipal();
        System.out.println("user = " + user);
        List<RouterInfo> routers    = routerService.findRouterByUserId(user.getId());
        val              findTree   = new FindTree();
        List<RouterInfo> routerList = findTree.findTree(routers);
        UserGetInfo      info       = new UserGetInfo();
        // lombok只能使单个类(不包含继承关系)使用的同一个类保持链式调用 ?
        info.setRouters(routerList);
        info.setId(user.getId())
                .setAvatar(user.getAvatar())
                .setUsername(user.getUsername())
                .setRoles(roleService.findByUserId(user.getId()))
                .setButtons(permissionService.getButtonByUserId(user.getId()));
        return Result.success(info);
    }
    /**
     * 用户信息
     */
    @PatchMapping("password")
    public Result updatePassword (String password, String oldPassword) {
        User user = (User) SecurityUtils.getSubject()
                .getPrincipal();
        // 密码不对
        if (! AuthUtils.testPassword(oldPassword, user.getPassword())) {
            return Result.failure(ResultCodeEnum.USER_PASSWORD_ERR);
        }
        userService.updatePasswordById(password, user.getId());
        return Result.operationSuccess();
    }
}
