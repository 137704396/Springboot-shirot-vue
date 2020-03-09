package kim.nzxy.auth.common.advice;

import kim.nzxy.auth.common.entity.Result;
import kim.nzxy.auth.common.enums.ResultCodeEnum;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常拦截
 *
 * @author Xiaoyan
 * @date 2019/11/19 17:47
 */
@RestControllerAdvice(basePackages = "kim.nzxy.auth")
public class ErrAdvisor {
    /**
     * @param e 无访问权限
     *
     * @return PERMISSION_NO_ACCESS
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result unauthorizedExceptionHandler (UnauthorizedException e) {
        e.printStackTrace();
        return Result.failure(ResultCodeEnum.PERMISSION_NO_ACCESS);
    }
    /**
     * @param e Exception
     *
     * @return SYSTEM_INNER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler (Exception e) {
        e.printStackTrace();
        return Result.failure(ResultCodeEnum.SYSTEM_INNER_ERROR);
    }
}
