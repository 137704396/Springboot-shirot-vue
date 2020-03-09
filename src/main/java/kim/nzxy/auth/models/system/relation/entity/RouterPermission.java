package kim.nzxy.auth.models.system.relation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/12/27 10:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterPermission implements Serializable {
    /**
     * router_id
     */
    private Integer router;

    /**
     * permission_id
     */
    private Integer permission;
}
