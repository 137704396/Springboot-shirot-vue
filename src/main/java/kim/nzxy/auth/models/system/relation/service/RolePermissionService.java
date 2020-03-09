package kim.nzxy.auth.models.system.relation.service;

import kim.nzxy.auth.models.system.relation.entity.RolePermission;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface RolePermissionService {
    int insert (RolePermission record);
    List<Integer> findPermissionByRole (Integer role);

    int deleteByRoleAndPermission (Integer role, Integer permission);

}
