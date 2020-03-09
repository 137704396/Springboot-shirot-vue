package kim.nzxy.auth.models.system.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kim.nzxy.auth.models.system.role.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 登录时获取的用户信息
 *
 * @author xy
 */
@Data
@Accessors(chain = true)
public class UserGetInfo implements Serializable {
    private Integer          id;
    private String           username;
    @JsonIgnore
    private String           password;
    private String           avatar;
    private Set<Role>        roles;
    private List<RouterInfo> routers;
    private Set<String>      buttons;
}
