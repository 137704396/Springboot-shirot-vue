package kim.nzxy.auth.models.system.permission;

import java.util.List;
import java.util.Set;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface PermissionService {
    Permission insert (Permission record);
    List<Permission> getByRouter (Integer router);
    int deleteById (Integer id);
    List<Permission> getAllByUserId (Integer userId);
    int updateById (Permission updated);
    Set<String> getButtonByUserId (Integer id);
    Set<String> getPortByUserId (Integer id);
}






