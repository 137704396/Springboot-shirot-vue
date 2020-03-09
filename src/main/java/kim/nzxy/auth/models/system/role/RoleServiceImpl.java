package kim.nzxy.auth.models.system.role;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Override
    public Role insert (Role record) {
        roleMapper.insert(record);
        return record;
    }
    @Override
    public Set<Role> findByUserId (Integer id) {
        return roleMapper.findByUserId(id);
    }
    @Override
    public PageInfo<Role> search (Role role, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(roleMapper.findByAll(role));
    }
    @Override
    public Role updateById (Role updated) {
        roleMapper.updateById(updated, updated.getId());
        return updated;
    }

    @Override
    public int deleteById (Integer id) {
        return roleMapper.deleteById(id);
    }

}
