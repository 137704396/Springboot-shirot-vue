package kim.nzxy.auth.models.system.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/30 10:07
 */
@Mapper
public interface UserMapper {
    int insert (User record);
    int insertSelective (User record);
    int updateByPrimaryKeySelective (User record);
    User findById (@Param("id") Integer id);
    User findByIdAndDelFalse (@Param("id") Integer id);
    User findByIdAndDelFalseAndEnableTrue (@Param("id") Integer id);
    User findByUsername (@Param("username") String username);
    User findByUsernameAndDelFalseAndEnableTrue (@Param("username") String username);
    List<User> findByAll (User user);
    int updateDelById (@Param("updatedDel") Boolean updatedDel, @Param("id") Integer id);
    int updatePasswordById (@Param("updatedPassword") String updatedPassword, @Param("id") Integer id);
    int updateEnableById (@Param("updatedEnable") Boolean updatedEnable, @Param("id") Integer id);
}
