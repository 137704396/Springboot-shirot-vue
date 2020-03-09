package kim.nzxy.auth.models.system.relation.service.impl;

import kim.nzxy.auth.models.system.relation.entity.RouterPermission;
import kim.nzxy.auth.models.system.relation.mapper.RouterPermissionMapper;
import kim.nzxy.auth.models.system.relation.service.RouterPermissionService;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/27 10:30
 */
@Service
public class RouterPermissionServiceImpl implements RouterPermissionService {

    @Resource
    private RouterPermissionMapper routerPermissionMapper;

    @Override
    public int insert (RouterPermission record) {
        return routerPermissionMapper.insert(record);
    }
    @Override
    public List<Integer> findPermissionByRouter (Integer router) {
        return routerPermissionMapper.findPermissionByRouter(router);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRelationRouterPermission (Integer routerId, Integer[] permissionIds) {
        routerPermissionMapper.deleteByRouter(routerId);
        val list = new ArrayList<RouterPermission>();
        for (Integer permissionId : permissionIds) {
            list.add(new RouterPermission(routerId, permissionId));
        }
        if (! list.isEmpty()) {
            routerPermissionMapper.insertList(list);
        }
    }
}
