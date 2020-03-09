package kim.nzxy.auth.models.system.router;

import kim.nzxy.auth.models.system.auth.RouterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/26 14:00
 */
@Mapper
public interface RouterMapper {
    int insert (Router record);
    List<RouterInfo> findRouterByUserId (Integer id);
    List<RouterInfo> findAll ();
    List<RouterInfo> findAllOrderByOrder ();
    int updateById (@Param("updated") Router updated, @Param("id") Integer id);
}
