package kim.nzxy.auth.models.system.relation.service.impl;

import kim.nzxy.auth.models.system.relation.entity.UserRole;
import kim.nzxy.auth.models.system.relation.mapper.UserRoleMapper;
import kim.nzxy.auth.models.system.relation.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    public int insert (UserRole record) {
        return userRoleMapper.insert(record);
    }
    @Override
    public List<Integer> findRoleByUser (Integer user) {
        return userRoleMapper.findRoleByUser(user);
    }
    @Override
    public int deleteByRoleAndUser (Integer role, Integer user) {
        return userRoleMapper.deleteByRoleAndUser(role, user);
    }

}
