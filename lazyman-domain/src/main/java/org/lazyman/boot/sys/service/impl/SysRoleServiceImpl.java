package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.common.constant.DataScope;
import org.lazyman.boot.sys.dto.SysRoleDataScopeFormDTO;
import org.lazyman.boot.sys.dto.SysRoleFormDTO;
import org.lazyman.boot.sys.dto.SysRoleQueryDTO;
import org.lazyman.boot.sys.entity.SysDept;
import org.lazyman.boot.sys.entity.SysMenu;
import org.lazyman.boot.sys.entity.SysRole;
import org.lazyman.boot.sys.mapper.SysRoleMapper;
import org.lazyman.boot.sys.service.*;
import org.lazyman.boot.sys.vo.SysRoleVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private ISysRoleMenuService iSysRoleMenuService;
    @Resource
    private ISysRoleDeptService iSysRoleDeptService;
    @Resource
    private ISysMenuService iSysMenuService;
    @Resource
    private ISysDeptService iSysDeptService;

    @Override
    public Boolean exists(SysRoleFormDTO sysRoleFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysRole>query().lambda()
                .eq(StrUtil.isNotBlank(sysRoleFormDTO.getRoleName()), SysRole::getRoleName, sysRoleFormDTO.getRoleName())
                .eq(StrUtil.isNotBlank(sysRoleFormDTO.getRoleCode()), SysRole::getRoleCode, sysRoleFormDTO.getRoleCode())
                .ne(ObjectUtil.isNotNull(sysRoleFormDTO.getId()), SysRole::getId, sysRoleFormDTO.getId())));
    }

    @Override
    public SysRole existsById(Long id) {
        SysRole sysRole = getById(id);
        if (ObjectUtil.isNull(sysRole)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysRole;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysRoleFormDTO sysRoleFormDTO) {
        if (exists(sysRoleFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysRole sysRole = BeanUtil.copyProperties(sysRoleFormDTO, SysRole.class);
        sysRole.setDataScope(DataScope.USER.getCode());
        save(sysRole);
        iSysRoleMenuService.createRoleMenu(sysRole.getId(), sysRoleFormDTO.getMenuIds());
        return sysRole.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysRoleFormDTO sysRoleFormDTO) {
        existsById(sysRoleFormDTO.getId());
        if (exists(sysRoleFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysRole sysRole = BeanUtil.copyProperties(sysRoleFormDTO, SysRole.class);
        return iSysRoleMenuService.createRoleMenu(sysRole.getId(), sysRoleFormDTO.getMenuIds()) && updateById(sysRole);
    }

    @Override
    public SysRoleVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysRoleVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysRoleVO> listByPage(SysRoleQueryDTO sysRoleQueryDTO) {
        Page<SysRole> sysRolePage = new Page<>(sysRoleQueryDTO.getPageNo(), sysRoleQueryDTO.getPageSize());
        IPage<SysRoleVO> sysRoleVOIPage = page(sysRolePage, Wrappers.<SysRole>query().lambda()
                .like(StrUtil.isNotBlank(sysRoleQueryDTO.getRoleName()), SysRole::getRoleName, sysRoleQueryDTO.getRoleName())
                .like(StrUtil.isNotBlank(sysRoleQueryDTO.getRoleCode()), SysRole::getRoleCode, sysRoleQueryDTO.getRoleCode())
                .between(StrUtil.isAllNotBlank(sysRoleQueryDTO.getBeginTime(), sysRoleQueryDTO.getEndTime()), SysRole::getCreateTime, sysRoleQueryDTO.getBeginTime(), sysRoleQueryDTO.getEndTime())
                .orderByDesc(SysRole::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysRoleVO.class));
        return new PageVO<>(sysRoleVOIPage.getTotal(), sysRoleVOIPage.getRecords());
    }

    @Override
    public List<SysRoleVO> listSelectOptions(String keyword) {
        List<SysRoleVO> selectOptionVOList = new ArrayList<>();
        List<SysRole> sysRoleList = list(Wrappers.<SysRole>query().lambda()
                .like(StrUtil.isNotBlank(keyword), SysRole::getRoleName, keyword)
                .orderByAsc(SysRole::getCreateTime));
        if (CollectionUtil.isEmpty(sysRoleList)) {
            return selectOptionVOList;
        }
        return sysRoleList.stream().map(v -> BeanUtil.copyProperties(v, SysRoleVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysRole>update().lambda().eq(SysRole::getId, stateActionDTO.getId())
                .eq(SysRole::getState, !stateActionDTO.getState())
                .set(SysRole::getState, stateActionDTO.getState()));
    }

    @Override
    public List<Long> listRoleMenuPermissions(Long roleId) {
        List<Long> menuIds = iSysRoleMenuService.listRoleMenuIds(Arrays.asList(roleId));
        if (CollectionUtil.isEmpty(menuIds)) {
            return menuIds;
        }
        List<SysMenu> sysMenuList = iSysMenuService.list(Wrappers.<SysMenu>query().lambda()
                .eq(SysMenu::getState, true)
                .in(SysMenu::getId, menuIds));
        if (CollectionUtil.isEmpty(sysMenuList)) {
            return new ArrayList<>();
        }
        return sysMenuList.stream().map(SysMenu::getId).collect(Collectors.toList());
    }

    @Override
    public Boolean setDataScope(SysRoleDataScopeFormDTO sysRoleDataScopeFormDTO) {
        existsById(sysRoleDataScopeFormDTO.getId());
        updateByWrapper(Wrappers.<SysRole>update().lambda().eq(SysRole::getId, sysRoleDataScopeFormDTO.getId())
                .set(SysRole::getDataScope, sysRoleDataScopeFormDTO.getDataScope()));
        return iSysRoleDeptService.createRoleDept(sysRoleDataScopeFormDTO.getId(), sysRoleDataScopeFormDTO.getDeptIds());
    }

    @Override
    public List<Long> listRoleDataScope(Long roleId) {
        List<Long> deptIds = iSysRoleDeptService.listRoleDeptIds(Arrays.asList(roleId));
        if (CollectionUtil.isEmpty(deptIds)) {
            return deptIds;
        }
        List<SysDept> sysDeptList = iSysDeptService.list(Wrappers.<SysDept>query().lambda()
                .eq(SysDept::getState, true)
                .in(SysDept::getId, deptIds));
        if (CollectionUtil.isEmpty(sysDeptList)) {
            return new ArrayList<>();
        }
        return sysDeptList.stream().map(SysDept::getId).collect(Collectors.toList());
    }
}
