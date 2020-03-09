package kim.nzxy.auth.models.system.router;

import kim.nzxy.auth.models.system.auth.RouterInfo;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface RouterService {
    Router insert (Router record);
    List<RouterInfo> findRouterByUserId (Integer id);
    List<RouterInfo> findAll ();
    int updateById (Router updated);

}



