package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.sys.entity.SysRole;
import org.lazyman.boot.sys.entity.SysUserRole;
import org.lazyman.boot.sys.mapper.SysUserRoleMapper;
import org.lazyman.boot.sys.service.ISysRoleService;
import org.lazyman.boot.sys.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Resource
    private ISysRoleService iSysRoleService;

    @Override
    public List<Long> listUserRoleIds(Long userId) {
        List<Long> roleIdList = new ArrayList<>();
        List<SysUserRole> sysUserRoleList = list(Wrappers.<SysUserRole>query().lambda().eq(SysUserRole::getUserId, userId));
        if (CollectionUtil.isEmpty(sysUserRoleList)) {
            return roleIdList;
        }
        roleIdList = sysUserRoleList.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
        return roleIdList;
    }

    @Override
    public Boolean createUserRole(Long userId, List<Long> roleIds) {
        List<SysUserRole> sysUserRoleList = list(Wrappers.<SysUserRole>query().lambda().eq(SysUserRole::getUserId, userId));
        if (CollectionUtil.isNotEmpty(sysUserRoleList)) {
            removeByIds(sysUserRoleList.stream().map(SysUserRole::getId).collect(Collectors.toList()));
        }
        if (CollectionUtil.isEmpty(roleIds)) {
            return false;
        }
        List<SysRole> sysRoleList = iSysRoleService.list(Wrappers.<SysRole>query().lambda().in(SysRole::getId, roleIds));
        if (CollectionUtil.isEmpty(sysRoleList)) {
            return false;
        }
        sysUserRoleList.clear();
        sysRoleList.stream().forEach(p -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(p.getId());
            sysUserRoleList.add(sysUserRole);
        });
        return saveBatch(sysUserRoleList);
    }
}
