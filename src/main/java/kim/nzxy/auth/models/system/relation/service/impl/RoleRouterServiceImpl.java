package kim.nzxy.auth.models.system.relation.service.impl;

import kim.nzxy.auth.models.system.relation.entity.RoleRouter;
import kim.nzxy.auth.models.system.relation.mapper.RoleRouterMapper;
import kim.nzxy.auth.models.system.relation.service.RoleRouterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
public class RoleRouterServiceImpl implements RoleRouterService {

    @Resource
    private RoleRouterMapper roleRouterMapper;

    @Override
    public int insert (RoleRouter roleRouter) {
        return roleRouterMapper.insert(roleRouter);
    }

    @Override
    public List<Integer> findRouterByRole (Integer role) {
        return roleRouterMapper.findRouterByRole(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByRoleAndRouter (Integer role, Integer router) {
        roleRouterMapper.deleteRelationRolePermissionByRoleAndRouter(role, router);
        return roleRouterMapper.deleteByRoleAndRouter(role, router);
    }

}


