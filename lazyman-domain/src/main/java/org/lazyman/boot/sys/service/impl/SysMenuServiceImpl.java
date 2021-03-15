package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.common.constant.MenuType;
import org.lazyman.boot.sys.dto.SysMenuFormDTO;
import org.lazyman.boot.sys.dto.SysMenuQueryDTO;
import org.lazyman.boot.sys.entity.SysMenu;
import org.lazyman.boot.sys.mapper.SysMenuMapper;
import org.lazyman.boot.sys.service.ISysMenuService;
import org.lazyman.boot.sys.service.ISysRoleMenuService;
import org.lazyman.boot.sys.vo.*;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.exception.BizException;
import org.lazyman.boot.wrapper.Wrapper;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Resource
    private ISysRoleMenuService iSysRoleMenuService;

    @Override
    public Boolean exists(SysMenuFormDTO sysMenuFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysMenu>query().lambda()
                .eq(ObjectUtil.isNotEmpty(sysMenuFormDTO.getMenuType()), SysMenu::getMenuType, sysMenuFormDTO.getMenuType())
                .eq(StrUtil.isNotBlank(sysMenuFormDTO.getMenuName()), SysMenu::getMenuName, sysMenuFormDTO.getMenuName())
                .eq(StrUtil.isNotBlank(sysMenuFormDTO.getMenuCode()), SysMenu::getMenuCode, sysMenuFormDTO.getMenuCode())
                .ne(ObjectUtil.isNotNull(sysMenuFormDTO.getId()), SysMenu::getId, sysMenuFormDTO.getId())));
    }

    @Override
    public SysMenu existsById(Long id) {
        SysMenu sysMenu = getById(id);
        if (ObjectUtil.isNull(sysMenu)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysMenu;
    }

    @Override
    public Long save(SysMenuFormDTO sysMenuFormDTO) {
        if (exists(sysMenuFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysMenu sysMenu = BeanUtil.copyProperties(sysMenuFormDTO, SysMenu.class);
        save(sysMenu);
        return sysMenu.getId();
    }

    @Override
    public Boolean edit(SysMenuFormDTO sysMenuFormDTO) {
        existsById(sysMenuFormDTO.getId());
        if (exists(sysMenuFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(sysMenuFormDTO, SysMenu.class));
    }

    @Override
    public SysMenuVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysMenuVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysMenuVO> listByPage(SysMenuQueryDTO sysMenuQueryDTO) {
        Page<SysMenu> sysMenuPage = new Page<>(sysMenuQueryDTO.getPageNo(), sysMenuQueryDTO.getPageSize());
        IPage<SysMenuVO> sysMenuVOIPage = page(sysMenuPage, Wrappers.<SysMenu>query().lambda()
                .like(StrUtil.isNotEmpty(sysMenuQueryDTO.getMenuName()), SysMenu::getMenuName, sysMenuQueryDTO.getMenuName())
                .eq(ObjectUtil.isNotEmpty(sysMenuQueryDTO.getState()), SysMenu::getState, sysMenuQueryDTO.getState())
                .orderByDesc(SysMenu::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysMenuVO.class));
        return new PageVO<>(sysMenuVOIPage.getTotal(), sysMenuVOIPage.getRecords());
    }


    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysMenu>update().lambda().eq(SysMenu::getId, stateActionDTO.getId())
                .eq(SysMenu::getState, !stateActionDTO.getState())
                .set(SysMenu::getState, stateActionDTO.getState()));
    }

    /**
     * 构建树列表
     *
     * @param sysMenuParentIdMap
     * @param sysMenuList
     * @param sysMenuTreeVOList
     */
    private void buildTreeList(Map<Long, List<SysMenu>> sysMenuParentIdMap, List<SysMenu> sysMenuList, List<SysMenuTreeVO> sysMenuTreeVOList) {
        sysMenuList = sysMenuList.stream().sorted(Comparator.comparing(SysMenu::getSort)).collect(Collectors.toList());
        sysMenuList.stream().forEach(p -> {
            SysMenuTreeVO sysMenuTreeVO = BeanUtil.copyProperties(p, SysMenuTreeVO.class);
            sysMenuTreeVOList.add(sysMenuTreeVO);
            List<SysMenu> childrens = sysMenuParentIdMap.get(p.getId());
            if (CollectionUtil.isEmpty(childrens)) {
                sysMenuTreeVO.setChildren(new ArrayList<>());
                return;
            }
            List<SysMenuTreeVO> childrensVOList = new ArrayList<>();
            buildTreeList(sysMenuParentIdMap, childrens, childrensVOList);
            sysMenuTreeVO.setChildren(childrensVOList);
        });
    }

    @Override
    public List<SysMenuTreeVO> listByTree(SysMenuQueryDTO sysMenuQueryDTO) {
        List<SysMenuTreeVO> sysMenuTreeVOList = new ArrayList<>();
        List<SysMenu> sysMenuList = list(Wrappers.<SysMenu>query().lambda()
                .like(StrUtil.isNotEmpty(sysMenuQueryDTO.getMenuName()), SysMenu::getMenuName, sysMenuQueryDTO.getMenuName())
                .eq(ObjectUtil.isNotEmpty(sysMenuQueryDTO.getState()), SysMenu::getState, sysMenuQueryDTO.getState())
        );
        if (CollectionUtil.isEmpty(sysMenuList)) {
            return sysMenuTreeVOList;
        }
        Map<Long, List<SysMenu>> sysMenuParentIdMap = sysMenuList.stream().collect(Collectors.groupingBy(e -> e.getParentId()));
        List<SysMenu> sysMenuRootList = sysMenuParentIdMap.get(CollectionUtil.min(sysMenuParentIdMap.keySet()));
        if (CollectionUtil.isEmpty(sysMenuRootList)) {
            return sysMenuTreeVOList;
        }
        buildTreeList(sysMenuParentIdMap, sysMenuRootList, sysMenuTreeVOList);
        return sysMenuTreeVOList;
    }

    /**
     * 构建vue路由树列表
     *
     * @param sysMenuParentIdMap
     * @param sysMenuList
     * @param vueRouterTreeVOList
     */
    private void buildVueRouterTreeList(Map<Long, List<SysMenu>> sysMenuParentIdMap, List<SysMenu> sysMenuList, List<VueRouterTreeVO> vueRouterTreeVOList) {
        sysMenuList = sysMenuList.stream().sorted(Comparator.comparing(SysMenu::getSort)).collect(Collectors.toList());
        sysMenuList.stream().forEach(p -> {
            VueRouterTreeVO vueRouterTreeVO = BeanUtil.copyProperties(p, VueRouterTreeVO.class);
            ((Wrapper<SysMenu, VueRouterTreeVO>) (e, v) -> {
                v.setName(e.getPath());
                if (MenuType.DIRECTORY.getCode().equals(e.getMenuType())) {
                    v.setAlwaysShow(true);
                    v.setRedirect("noRedirect");
                    v.setPath(StringPool.SLASH + e.getPath());
                    v.setComponent(LazymanConstant.MENU_ROOT_ID.equals(e.getParentId()) ? "Layout" : "ParentView");
                }
                v.setHidden(!e.getIsVisible());
                VueRouterMetaVO vueRouterMetaVO = new VueRouterMetaVO();
                vueRouterMetaVO.setTitle(e.getMenuName());
                vueRouterMetaVO.setIcon(e.getIcon());
                vueRouterMetaVO.setNoCache(!e.getIsCache());
                v.setMeta(vueRouterMetaVO);
            }).wrap(p, vueRouterTreeVO);
            vueRouterTreeVOList.add(vueRouterTreeVO);
            List<SysMenu> childrens = sysMenuParentIdMap.get(p.getId());
            if (CollectionUtil.isEmpty(childrens)) {
                vueRouterTreeVO.setChildren(new ArrayList<>());
                return;
            }
            List<VueRouterTreeVO> childrensVOList = new ArrayList<>();
            buildVueRouterTreeList(sysMenuParentIdMap, childrens, childrensVOList);
            vueRouterTreeVO.setChildren(childrensVOList);
        });
    }

    @Override
    public List<VueRouterTreeVO> listVueRouterByTree(List<Long> roles) {
        List<VueRouterTreeVO> vueRouterTreeVOList = new ArrayList<>();
        List<Long> menuIds = iSysRoleMenuService.listRoleMenuIds(roles);
        if (CollectionUtil.isEmpty(menuIds)) {
            return vueRouterTreeVOList;
        }
        List<SysMenu> sysMenuList = list(Wrappers.<SysMenu>query().lambda()
                .in(SysMenu::getId, menuIds).eq(SysMenu::getState, true)
                .in(SysMenu::getMenuType, Arrays.asList(MenuType.DIRECTORY.getCode(), MenuType.MENU.getCode()))
        );
        if (CollectionUtil.isEmpty(sysMenuList)) {
            return vueRouterTreeVOList;
        }
        Map<Long, List<SysMenu>> sysMenuParentIdMap = sysMenuList.stream().collect(Collectors.groupingBy(e -> e.getParentId()));
        List<SysMenu> sysMenuRootList = sysMenuParentIdMap.get(CollectionUtil.min(sysMenuParentIdMap.keySet()));
        if (CollectionUtil.isEmpty(sysMenuRootList)) {
            return vueRouterTreeVOList;
        }
        buildVueRouterTreeList(sysMenuParentIdMap, sysMenuRootList, vueRouterTreeVOList);
        return vueRouterTreeVOList;
    }

    /**
     * 构建vue路由树列表
     *
     * @param sysMenuParentIdMap
     * @param sysMenuList
     * @param vueElTreeVOList
     */
    private void buildVueElPermissionTreeList(Map<Long, List<SysMenu>> sysMenuParentIdMap, List<SysMenu> sysMenuList, List<VueElTreeVO> vueElTreeVOList) {
        sysMenuList = sysMenuList.stream().sorted(Comparator.comparing(SysMenu::getSort)).collect(Collectors.toList());
        sysMenuList.stream().forEach(p -> {
            VueElTreeVO vueElTreeVO = BeanUtil.copyProperties(p, VueElTreeVO.class);
            ((Wrapper<SysMenu, VueElTreeVO>) (e, v) -> {
                v.setId(e.getId());
                v.setLabel(e.getMenuName());
            }).wrap(p, vueElTreeVO);
            vueElTreeVOList.add(vueElTreeVO);
            List<SysMenu> childrens = sysMenuParentIdMap.get(p.getId());
            if (CollectionUtil.isEmpty(childrens)) {
                vueElTreeVO.setChildren(new ArrayList<>());
                return;
            }
            List<VueElTreeVO> childrensVOList = new ArrayList<>();
            buildVueElPermissionTreeList(sysMenuParentIdMap, childrens, childrensVOList);
            vueElTreeVO.setChildren(childrensVOList);
        });
    }

    @Override
    public List<VueElTreeVO> listVueElPermissionByTree() {
        List<VueElTreeVO> vueElTreeVOList = new ArrayList<>();
        List<SysMenu> sysMenuList = list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getState, true));
        if (CollectionUtil.isEmpty(sysMenuList)) {
            return vueElTreeVOList;
        }
        Map<Long, List<SysMenu>> sysMenuParentIdMap = sysMenuList.stream().collect(Collectors.groupingBy(e -> e.getParentId()));
        List<SysMenu> sysMenuRootList = sysMenuParentIdMap.get(CollectionUtil.min(sysMenuParentIdMap.keySet()));
        if (CollectionUtil.isEmpty(sysMenuRootList)) {
            return vueElTreeVOList;
        }
        buildVueElPermissionTreeList(sysMenuParentIdMap, sysMenuRootList, vueElTreeVOList);
        return vueElTreeVOList;
    }

    @Override
    public List<SysMenuVO> listChildrensByParentId(Long parentId) {
        List<SysMenu> sysMenuList = list(Wrappers.<SysMenu>query().lambda()
                .eq(SysMenu::getParentId, parentId)
        );
        if (CollectionUtil.isEmpty(sysMenuList)) {
            return new ArrayList<>();
        }
        sysMenuList = sysMenuList.stream().sorted(Comparator.comparing(SysMenu::getSort)).collect(Collectors.toList());
        return sysMenuList.stream().map(m -> BeanUtil.copyProperties(m, SysMenuVO.class)).collect(Collectors.toList());
    }
}
