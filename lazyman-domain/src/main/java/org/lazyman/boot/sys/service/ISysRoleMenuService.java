package org.lazyman.boot.sys.service;

import org.lazyman.boot.sys.entity.SysRoleMenu;
import org.lazyman.core.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
public interface ISysRoleMenuService extends BaseService<SysRoleMenu> {
    /**
     * 查询角色权限ID列表
     *
     * @param roleIds
     * @return
     */
    List<Long> listRoleMenuIds(List<Long> roleIds);

    /**
     * 创建角色菜单
     *
     * @param roleId
     * @param menus
     */
    Boolean createRoleMenu(Long roleId, List<Long> menus);
}
