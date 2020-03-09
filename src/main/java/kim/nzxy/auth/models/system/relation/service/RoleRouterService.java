package kim.nzxy.auth.models.system.relation.service;

import kim.nzxy.auth.models.system.relation.entity.RoleRouter;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface RoleRouterService {

    int insert (RoleRouter roleRouter);

    List<Integer> findRouterByRole (Integer role);

    int deleteByRoleAndRouter (Integer role, Integer router);

}


