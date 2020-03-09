package kim.nzxy.auth.models.system.token;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Mapper
public interface SysTokenMapper {
    int insert (SysToken record);
    SysToken findByUserId (@Param("userId") Integer userId);
    int updateByUserId (@Param("updated") SysToken updated, @Param("userId") Integer userId);
    SysToken findByToken (@Param("token") String token);
}
