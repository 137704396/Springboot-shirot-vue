package kim.nzxy.auth.models.system.permission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author Xiaoyan
 * @date 2019/12/31 17:41
 */
@Mapper
public interface PermissionMapper {
    int insert (Permission record);
    List<Permission> getByRouter (@Param("router") Integer router);
    int deleteById (@Param("id") Integer id);
    List<Permission> getAllByUserId (Integer userId);
    Set<String> getButtonByUserId (Integer id);
    Set<String> getPortByUserId (Integer id);
    int updateById (@Param("updated") Permission updated, @Param("id") Integer id);
}
