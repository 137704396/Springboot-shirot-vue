package kim.nzxy.auth.models.system.role;

import com.github.pagehelper.PageInfo;

import java.util.Set;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface RoleService {
    Role insert (Role record);
    Set<Role> findByUserId (Integer id);
    PageInfo<Role> search (Role role, int page, int pageSize);
    Role updateById (Role updated);

    int deleteById (Integer id);

}
