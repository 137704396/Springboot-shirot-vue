package kim.nzxy.auth.models.system;

import kim.nzxy.auth.common.entity.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xy
 */
@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("selectUser")
    @RequiresPermissions("selectUser")
    public Result selectUser () {
        return Result.operationSuccess();
    }
    @GetMapping("deleteUser")
    @RequiresPermissions({"deleteUser"})
    public Result deleteUser () {
        return Result.operationSuccess();
    }
}
