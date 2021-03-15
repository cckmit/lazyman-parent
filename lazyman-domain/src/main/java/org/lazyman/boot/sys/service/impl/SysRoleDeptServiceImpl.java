package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lazyman.boot.sys.entity.SysDept;
import org.lazyman.boot.sys.entity.SysRoleDept;
import org.lazyman.boot.sys.mapper.SysRoleDeptMapper;
import org.lazyman.boot.sys.service.ISysDeptService;
import org.lazyman.boot.sys.service.ISysRoleDeptService;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色部门数据权限 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Service
public class SysRoleDeptServiceImpl extends BaseServiceImpl<SysRoleDeptMapper, SysRoleDept> implements ISysRoleDeptService {
    @Resource
    private ISysDeptService iSysDeptService;

    @Override
    public List<Long> listRoleDeptIds(List<Long> roleIds) {
        List<Long> deptIdList = new ArrayList<>();
        List<SysRoleDept> sysRoleDeptList = list(Wrappers.<SysRoleDept>query().lambda().in(SysRoleDept::getRoleId, roleIds));
        if (CollectionUtil.isEmpty(sysRoleDeptList)) {
            return deptIdList;
        }
        deptIdList = sysRoleDeptList.stream().map(SysRoleDept::getDeptId).distinct().collect(Collectors.toList());
        return deptIdList;
    }

    @Override
    public Boolean createRoleDept(Long roleId, List<Long> deptIds) {
        List<SysRoleDept> sysRoleDeptList = list(Wrappers.<SysRoleDept>query().lambda().eq(SysRoleDept::getRoleId, roleId));
        if (CollectionUtil.isNotEmpty(sysRoleDeptList)) {
            removeByIds(sysRoleDeptList.stream().map(SysRoleDept::getId).collect(Collectors.toList()));
        }
        if (CollectionUtil.isEmpty(deptIds)) {
            return false;
        }
        List<SysDept> sysDeptList = iSysDeptService.list(Wrappers.<SysDept>query().lambda().in(SysDept::getId, deptIds));
        if (CollectionUtil.isEmpty(sysDeptList)) {
            return false;
        }
        sysRoleDeptList.clear();
        sysDeptList.stream().forEach(p -> {
            SysRoleDept sysRoleDept = new SysRoleDept();
            sysRoleDept.setRoleId(roleId);
            sysRoleDept.setDeptId(p.getId());
            sysRoleDeptList.add(sysRoleDept);
        });
        return saveBatch(sysRoleDeptList);
    }
}
