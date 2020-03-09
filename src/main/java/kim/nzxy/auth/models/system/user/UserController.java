package kim.nzxy.auth.models.system.user;

import kim.nzxy.auth.common.config.Constant;
import kim.nzxy.auth.common.entity.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/12 14:30
 */
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final Constant constant;
    @GetMapping
    public Result search (User user,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(userService.search(user, page, pageSize));
    }
    @GetMapping("{id}")
    public Result getOne (@PathVariable Integer id) {
        return Result.success(userService.findById(id));
    }
    @PostMapping
    public Result insert (User user) {
        return Result.operationSuccess(userService.insertSelective(user));
    }
    @PutMapping
    public Result update (User user) {
        return Result.operationSuccess(userService.updateById(user));
    }
    @DeleteMapping("{id}")
    public Result delete (@PathVariable Integer id) {
        userService.deleteById(id);
        userService.logout(id);
        return Result.operationSuccess();
    }
    @PatchMapping("{id}")
    public Result resetPassword (@PathVariable Integer id) {
        userService.resetPassword(id);
        return Result.operationSuccess();
    }
    @PatchMapping("enable/{id}")
    public Result enableUser (@PathVariable Integer id) {
        userService.enableUser(id);
        return Result.operationSuccess();
    }
    @PatchMapping("disable/{id}")
    public Result disableUser (@PathVariable Integer id) {
        userService.disableUser(id);
        // 登出用户
        userService.logout(id);
        return Result.operationSuccess();
    }
}
