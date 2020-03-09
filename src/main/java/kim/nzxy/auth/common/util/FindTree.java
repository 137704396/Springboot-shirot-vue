package kim.nzxy.auth.common.util;

import kim.nzxy.auth.common.entity.Meta;
import kim.nzxy.auth.models.system.auth.RouterInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 仅仅用来生成目录树的
 * @author xy
 */
public class FindTree {

    /**
     * 排序,根据order排序
     */
    public Comparator<RouterInfo> order () {
        return (o1, o2) -> {
            if (! o1.getOrder()
                    .equals(o2.getOrder())) {
                return o1.getOrder() - o2.getOrder();
            }
            return 0;
        };
    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param allMenu 所有菜单列表
     *
     * @return 每个根节点下，所有子菜单列表
     */
    public List<RouterInfo> getChild (Integer id, List<RouterInfo> allMenu) {
        //子菜单
        List<RouterInfo> childList = new ArrayList<>();
        for (RouterInfo nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getParentId()
                    .equals(id)) {
                nav.setMeta(new Meta(nav.getTitle(), nav.getIcon()));
                childList.add(nav);
            }
        }
        return childList;
    }

    public List<RouterInfo> findTree (List<RouterInfo> routers) {
        List<RouterInfo> rootMenu = new ArrayList<>();
        for (RouterInfo nav : routers) {
            //父节点是0的，为根节点。
            if (nav.getParentId() == 0) {
                nav.setMeta(new Meta(nav.getTitle(), nav.getIcon()));
                if (createTree(nav, routers)) {
                    rootMenu.add(nav);
                }
            }
        }
        return rootMenu;

    }
    public boolean createTree (RouterInfo info, List<RouterInfo> infos) {
        if (! info.getAlwaysShow()) {
            return true;
        }
        List<RouterInfo> nodeChildren = getChild(info.getId(), infos);
        if (nodeChildren.isEmpty()) {
            return false;
        }
        List<RouterInfo> children = info.getChildren();
        for (RouterInfo nodeChild : nodeChildren) {
            if (nodeChild.getAlwaysShow()) {
                if (createTree(nodeChild, infos)) {
                    children.add(nodeChild);
                }
            } else {
                children.add(nodeChild);
            }
        }
        return ! children.isEmpty();
    }
}
