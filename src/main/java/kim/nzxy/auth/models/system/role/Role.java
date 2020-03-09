package kim.nzxy.auth.models.system.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Data
public class Role implements Serializable {
    private Integer id;

    private String name;

    private String remark;

}
