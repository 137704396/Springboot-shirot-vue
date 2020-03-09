package kim.nzxy.auth.models.system.relation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/12/31 13:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRouter implements Serializable {
    private static final long    serialVersionUID = 1L;
    /**
     * 角色id
     */
    private              Integer role;
    /**
     * 路由id
     */
    private              Integer router;
}
