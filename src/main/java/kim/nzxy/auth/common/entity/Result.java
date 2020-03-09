package kim.nzxy.auth.common.entity;

import kim.nzxy.auth.common.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/11/26 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    private Integer code;

    private String message;

    private Object data;

    public Result (Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success () {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        return result;
    }
    public static Result operationSuccess () {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.OPERATION_SUCCESS);
        return result;
    }
    public static Result operationSuccess (Object object) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.OPERATION_SUCCESS);
        result.setData(object);
        return result;
    }

    public static Result success (Object data) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result failure (ResultCodeEnum resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure (ResultCodeEnum resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    private void setResultCode (ResultCodeEnum code) {
        this.code = code.code();
        this.message = code.message();
    }
}
