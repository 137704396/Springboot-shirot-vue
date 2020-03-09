package kim.nzxy.auth.models.system.relation.mapper;

import kim.nzxy.auth.models.system.relation.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Mapper
public interface UserRoleMapper {
    int insert (UserRole record);
    List<Integer> findRoleByUser (@Param("user") Integer user);
    int deleteByRoleAndUser (@Param("role") Integer role, @Param("user") Integer user);
}
