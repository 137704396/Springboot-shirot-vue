package kim.nzxy.auth.models.system.relation.mapper;

import kim.nzxy.auth.models.system.relation.entity.RouterPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/27 10:30
 */
@Mapper
public interface RouterPermissionMapper {
    int insert (RouterPermission record);
    List<Integer> findPermissionByRouter (@Param("router") Integer router);
    int deleteByRouter (@Param("router") Integer router);
    int insertList (@Param("list") List<RouterPermission> list);

}
