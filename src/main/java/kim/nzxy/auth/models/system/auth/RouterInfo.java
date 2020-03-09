package kim.nzxy.auth.models.system.auth;

import kim.nzxy.auth.common.entity.Meta;
import kim.nzxy.auth.models.system.router.Router;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 18:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RouterInfo extends Router {
    private Meta             meta;
    private List<RouterInfo> children = new ArrayList<>();
}
