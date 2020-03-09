package kim.nzxy.auth.models.system.router;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xiaoyan
 * @date 2019/12/26 14:00
 */
@Data
public class Router implements Serializable {
    private static final long    serialVersionUID = 1L;
    private              Integer id;
    /**
     * 路径
     */
    private              String  path;
    private              String  component;
    private              String  redirect;
    /**
     * 名称
     */
    private              String  name;
    /**
     * 是否显示
     */
    private              Boolean alwaysShow;
    /**
     * 图标
     */
    private              String  icon;
    /**
     * 标题
     */
    private              String  title;
    /**
     * 二级id
     */
    private              Integer parentId;
    /**
     * 顺序
     */
    private              Integer order;
    /**
     * 备注
     */
    private              String  description;
}
