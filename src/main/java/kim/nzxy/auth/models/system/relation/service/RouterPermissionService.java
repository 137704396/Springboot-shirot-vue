package kim.nzxy.auth.models.system.relation.service;

import kim.nzxy.auth.models.system.relation.entity.RouterPermission;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/27 10:30
 */
public interface RouterPermissionService {

    int insert (RouterPermission record);

    List<Integer> findPermissionByRouter (Integer router);
    void setRelationRouterPermission (Integer routerId, Integer[] permissionIds);
}
