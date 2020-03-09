package kim.nzxy.auth.models.system.relation.mapper;

import kim.nzxy.auth.models.system.relation.entity.RoleRouter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/31 13:50
 */
@Mapper
public interface RoleRouterMapper {
    int insert (RoleRouter record);
    List<Integer> findRouterByRole (@Param("role") Integer role);
    int deleteByRoleAndRouter (@Param("role") Integer role, @Param("router") Integer router);

    /**
     * 解除当前用户的路由时顺便解除此路由下角色关联的按钮权限
     *
     * @param role   角色id
     * @param router 路由id
     */
    void deleteRelationRolePermissionByRoleAndRouter (@Param("role") Integer role, @Param("router") Integer router);
}
