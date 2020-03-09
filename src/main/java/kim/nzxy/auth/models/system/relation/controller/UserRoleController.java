package kim.nzxy.auth.models.system.relation.controller;

import kim.nzxy.auth.common.entity.Result;
import kim.nzxy.auth.models.system.relation.entity.UserRole;
import kim.nzxy.auth.models.system.relation.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/26 15:23
 */
@RestController
@RequestMapping("relation")
@AllArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;
    @GetMapping("{userId}")
    public Result getRelationRoleIds (@PathVariable Integer userId) {
        return Result.success(userRoleService.findRoleByUser(userId));
    }

    @PostMapping("{userId}/{roleId}")
    public Result insertUserRole (@PathVariable Integer userId, @PathVariable Integer roleId) {
        userRoleService.insert(new UserRole(userId, roleId));
        return Result.operationSuccess();
    }
    @DeleteMapping("{userId}/{roleId}")
    public Result deleteUserRole (@PathVariable Integer userId, @PathVariable Integer roleId) {
        userRoleService.deleteByRoleAndUser(roleId, userId);
        return Result.operationSuccess();
    }
}
