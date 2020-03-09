package kim.nzxy.auth.models.system.router;

import kim.nzxy.auth.models.system.auth.RouterInfo;
import kim.nzxy.auth.common.util.FindTree;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
public class RouterServiceImpl implements RouterService {

    @Resource
    private RouterMapper routerMapper;

    @Override
    public Router insert (Router record) {
        routerMapper.insert(record);
        return record;
    }
    @Override
    public List<RouterInfo> findRouterByUserId (Integer id) {
        return routerMapper.findRouterByUserId(id);
    }
    @Override
    public List<RouterInfo> findAll () {
        val findTree = new FindTree();
        return findTree.findTree(routerMapper.findAllOrderByOrder());
    }
    @Override
    public int updateById (Router updated) {
        return routerMapper.updateById(updated, updated.getId());
    }
}



