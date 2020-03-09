package kim.nzxy.auth.models.system.router;

import kim.nzxy.auth.common.entity.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoyan
 * @date 2019/12/25 17:30
 */
@RestController
@RequestMapping("router")
@AllArgsConstructor
public class RouterController {
    private final RouterService routerService;
    @GetMapping
    public Result getAll () {
        return Result.success(routerService.findAll());
    }
    @PostMapping
    public Result insert (Router router) {
        return Result.operationSuccess(routerService.insert(router));
    }
    @PutMapping
    public Result update (Router router) {
        return Result.operationSuccess(routerService.updateById(router));
    }
}
