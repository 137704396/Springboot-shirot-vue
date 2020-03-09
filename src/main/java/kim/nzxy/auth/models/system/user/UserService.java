package kim.nzxy.auth.models.system.user;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface UserService {
    User insert (User record);
    User findById (Integer id);
    User findByUsername (String username);
    Map<String, Object> createToken (Integer id);
    void logout (Integer id);
    PageInfo<User> search (User user, int page, int pageSize);
    User updateById (User updated);
    void updatePasswordById (String password, Integer id);
    int deleteById (Integer id);
    int resetPassword (Integer id);
    int updateByPrimaryKeySelective (User record);
    User insertSelective (User user);
    void enableUser (Integer id);
    void disableUser (Integer id);
}





