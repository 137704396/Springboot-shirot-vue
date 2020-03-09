package kim.nzxy.auth.models.system.relation.controller;

import kim.nzxy.auth.common.entity.Result;
import kim.nzxy.auth.models.system.relation.entity.RolePermission;
import kim.nzxy.auth.models.system.relation.service.RolePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/31 14:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("relation/role/permission")
public class RolePermissionController {
    private RolePermissionService rolePermissionService;
    @GetMapping("{roleId}")
    public Result getRelationPermissionIds (@PathVariable Integer roleId) {
        return Result.success(rolePermissionService.findPermissionByRole(roleId));
    }
    @PostMapping("{roleId}/{permissionId}")
    public Result insertRolePermission (@PathVariable Integer roleId, @PathVariable Integer permissionId) {
        return Result.operationSuccess(rolePermissionService.insert(new RolePermission(roleId, permissionId)));
    }
    @DeleteMapping("{roleId}/{permissionId}")
    public Result deleteRolePermission (@PathVariable Integer roleId, @PathVariable Integer permissionId) {
        return Result.operationSuccess(rolePermissionService.deleteByRoleAndPermission(roleId, permissionId));
    }
}
