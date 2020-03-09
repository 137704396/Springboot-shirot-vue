package kim.nzxy.auth.models.system.relation.service;

import kim.nzxy.auth.models.system.relation.entity.UserRole;

import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface UserRoleService {

    int insert (UserRole record);
    List<Integer> findRoleByUser (Integer user);

    int deleteByRoleAndUser (Integer role, Integer user);

}
