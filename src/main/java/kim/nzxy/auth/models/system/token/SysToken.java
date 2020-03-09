package kim.nzxy.auth.models.system.token;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Data
public class SysToken implements Serializable {
    private Integer userId;

    private LocalDateTime expireTime;

    private String token;

    private LocalDateTime updateTime;

}
