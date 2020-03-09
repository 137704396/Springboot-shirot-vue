package kim.nzxy.auth.models.system.relation.mapper;

import kim.nzxy.auth.models.system.relation.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Mapper
public interface RolePermissionMapper {
    int insert (RolePermission record);
    List<Integer> findPermissionByRole (@Param("role") Integer role);
    int deleteByRoleAndPermission (@Param("role") Integer role, @Param("permission") Integer permission);

}
