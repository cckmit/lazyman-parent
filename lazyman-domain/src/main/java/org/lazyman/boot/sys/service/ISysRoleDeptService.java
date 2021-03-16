package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.sys.entity.SysRoleDept;

import java.util.List;

/**
 * <p>
 * 系统角色部门数据权限 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
public interface ISysRoleDeptService extends BaseService<SysRoleDept> {
    /**
     * 查询角色部门ID列表
     *
     * @param roleIds
     * @return
     */
    List<Long> listRoleDeptIds(List<Long> roleIds);

    /**
     * 创建角色部门
     *
     * @param roleId
     * @param deptIds
     */
    Boolean createRoleDept(Long roleId, List<Long> deptIds);
}
