package kim.nzxy.auth.models.system.role;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Mapper
public interface RoleMapper {
    int insert (Role record);
    Set<Role> findByUserId (Integer userId);
    List<Role> findByAll (Role role);
    int updateById (@Param("updated") Role updated, @Param("id") Integer id);
    int deleteById (@Param("id") Integer id);

}
