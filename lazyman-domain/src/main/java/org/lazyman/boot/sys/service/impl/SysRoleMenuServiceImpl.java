package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.sys.entity.SysMenu;
import org.lazyman.boot.sys.entity.SysRoleMenu;
import org.lazyman.boot.sys.mapper.SysRoleMenuMapper;
import org.lazyman.boot.sys.service.ISysMenuService;
import org.lazyman.boot.sys.service.ISysRoleMenuService;
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
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    @Resource
    private ISysMenuService iSysMenuService;

    @Override
    public List<Long> listRoleMenuIds(List<Long> roleIds) {
        List<Long> menuIdList = new ArrayList<>();
        List<SysRoleMenu> sysRoleMenuList = list(Wrappers.<SysRoleMenu>query().lambda().in(SysRoleMenu::getRoleId, roleIds));
        if (CollectionUtil.isEmpty(sysRoleMenuList)) {
            return menuIdList;
        }
        menuIdList = sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
        return menuIdList;
    }

    @Override
    public Boolean createRoleMenu(Long roleId, List<Long> menus) {
        List<SysRoleMenu> sysRoleMenuList = list(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, roleId));
        if (CollectionUtil.isNotEmpty(sysRoleMenuList)) {
            removeByIds(sysRoleMenuList.stream().map(SysRoleMenu::getId).collect(Collectors.toList()));
        }
        if (CollectionUtil.isEmpty(menus)) {
            return false;
        }
        List<SysMenu> sysMenuList = iSysMenuService.list(Wrappers.<SysMenu>query().lambda().in(SysMenu::getId, menus));
        if (CollectionUtil.isEmpty(sysMenuList)) {
            return false;
        }
        sysRoleMenuList.clear();
        sysMenuList.stream().forEach(p -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(p.getId());
            sysRoleMenuList.add(sysRoleMenu);
        });
        return saveBatch(sysRoleMenuList);
    }
}
