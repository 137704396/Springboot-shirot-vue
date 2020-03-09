package kim.nzxy.auth.models.system.permission;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/12/31 17:41
 */
@Data
public class Permission implements Serializable {
    private Integer id;

    /**
     * 权限
     */
    private String button;

    /**
     * 按钮显示文字
     */
    private String text;

    /**
     * 对应的接口
     */
    private String port;

    /**
     * 对应路由id
     */
    private Integer router;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}
