package kim.nzxy.auth.models.system.relation.controller;

import kim.nzxy.auth.common.entity.Result;
import kim.nzxy.auth.models.system.relation.entity.RoleRouter;
import kim.nzxy.auth.models.system.relation.service.RoleRouterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/30 17:19
 */
@RestController
@RequestMapping("relation/role/router")
@AllArgsConstructor
public class RoleRouterController {
    private final RoleRouterService roleRouterService;
    @GetMapping("{id}")
    public Result getRouterIdsByRoleId (@PathVariable Integer id) {
        return Result.success(roleRouterService.findRouterByRole(id));
    }
    @PostMapping("{roleId}/{routerId}")
    public Result insertRoleRouter (@PathVariable Integer roleId, @PathVariable Integer routerId) {
        return Result.operationSuccess(roleRouterService.insert(new RoleRouter(roleId, routerId)));
    }
    @DeleteMapping("{roleId}/{routerId}")
    public Result deleteRoleRouter (@PathVariable Integer roleId, @PathVariable Integer routerId) {
        return Result.operationSuccess(roleRouterService.deleteByRoleAndRouter(roleId, routerId));
    }
}
