package kim.nzxy.auth.models.system.role;

import kim.nzxy.auth.common.entity.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/26 10:00
 */
@RestController
@RequestMapping("role")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping
    public Result search (Role role,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(roleService.search(role, page, pageSize));
    }
    @PostMapping
    public Result insert (Role role) {
        return Result.operationSuccess(roleService.insert(role));
    }
    @PutMapping
    public Result update (Role role) {
        return Result.operationSuccess(roleService.updateById(role));
    }
    @DeleteMapping("{id}")
    public Result delete (@PathVariable Integer id) {
        roleService.deleteById(id);
        return Result.operationSuccess();
    }
}
