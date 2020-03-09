package kim.nzxy.auth.models.system.permission;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public Permission insert (Permission record) {
        permissionMapper.insert(record);
        return record;
    }
    @Override
    public List<Permission> getByRouter (Integer router) {
        return permissionMapper.getByRouter(router);
    }
    @Override
    public int deleteById (Integer id) {
        return permissionMapper.deleteById(id);
    }
    @Override
    public List<Permission> getAllByUserId (Integer userId) {
        return permissionMapper.getAllByUserId(userId);
    }
    @Override
    public int updateById (Permission updated) {
        return permissionMapper.updateById(updated, updated.getId());
    }

    @Override
    public Set<String> getButtonByUserId (Integer id) {
        return permissionMapper.getButtonByUserId(id);
    }

    @Override
    public Set<String> getPortByUserId (Integer id) {
        return permissionMapper.getPortByUserId(id);
    }
}






