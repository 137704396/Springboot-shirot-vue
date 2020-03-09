package kim.nzxy.auth.models.system.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/12/30 10:07
 */
@Data
public class User implements Serializable {
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 备注
     */
    private String remark;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门
     */
    private String department;

    /**
     * 职务
     */
    private String job;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 删除标志,如果为true则用户不可见
     */
    @JsonIgnore
    private Boolean del;

    private static final long serialVersionUID = 1L;
}
