package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.sys.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
public interface ISysUserRoleService extends BaseService<SysUserRole> {
    /**
     * 查询用户角色ID列表
     *
     * @param userId
     * @return
     */
    List<Long> listUserRoleIds(Long userId);

    /**
     * 创建用户角色
     *
     * @param userId
     * @param roleIds
     */
    Boolean createUserRole(Long userId, List<Long> roleIds);
}
