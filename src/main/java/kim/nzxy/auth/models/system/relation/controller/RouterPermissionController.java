package kim.nzxy.auth.models.system.relation.controller;

import kim.nzxy.auth.common.entity.Result;
import kim.nzxy.auth.models.system.relation.service.RouterPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/27 10:23
 */
@RestController
@RequestMapping("relation/router/permission")
@AllArgsConstructor
public class RouterPermissionController {
    private final RouterPermissionService routerPermissionService;
    @GetMapping("{id}")
    public Result getRelationPermissionIds (@PathVariable Integer id) {
        return Result.success(routerPermissionService.findPermissionByRouter(id));
    }
    @PutMapping("{routerId}")
    public Result setRelationPermission (@PathVariable Integer routerId, Integer[] permissionIds) {
        routerPermissionService.setRelationRouterPermission(routerId, permissionIds);
        return Result.operationSuccess();
    }
}
