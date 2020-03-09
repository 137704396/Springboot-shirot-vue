package kim.nzxy.auth.models.system.permission;

import kim.nzxy.auth.common.entity.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/26 14:04
 */
@RestController
@RequestMapping("permission")
@AllArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @GetMapping("{routerId}")
    public Result getPermissionList (@PathVariable Integer routerId) {
        return Result.success(permissionService.getByRouter(routerId));
    }
    @PostMapping
    public Result insertPermission (Permission permission) {
        return Result.operationSuccess(permissionService.insert(permission));
    }
    @PutMapping
    public Result updatePermission (Permission permission) {
        return Result.operationSuccess(permissionService.updateById(permission));
    }
    @DeleteMapping("{id}")
    public Result delPermission (@PathVariable Integer id) {
        return Result.operationSuccess(permissionService.deleteById(id));
    }
}
