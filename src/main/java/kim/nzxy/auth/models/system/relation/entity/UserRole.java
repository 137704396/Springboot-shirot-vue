package kim.nzxy.auth.models.system.relation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    private Integer user;
    private Integer role;
}
