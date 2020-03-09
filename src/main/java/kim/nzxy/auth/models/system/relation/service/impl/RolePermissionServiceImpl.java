package kim.nzxy.auth.models.system.relation.service.impl;

import kim.nzxy.auth.models.system.relation.entity.RolePermission;
import kim.nzxy.auth.models.system.relation.mapper.RolePermissionMapper;
import kim.nzxy.auth.models.system.relation.service.RolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public int insert (RolePermission record) {
        return rolePermissionMapper.insert(record);
    }

    @Override
    public List<Integer> findPermissionByRole (Integer role) {
        return rolePermissionMapper.findPermissionByRole(role);
    }

    @Override
    public int deleteByRoleAndPermission (Integer role, Integer permission) {
        return rolePermissionMapper.deleteByRoleAndPermission(role, permission);
    }

}
