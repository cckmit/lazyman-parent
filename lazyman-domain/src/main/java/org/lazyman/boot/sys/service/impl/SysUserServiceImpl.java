package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.common.auth.vo.LoginUserInfoVO;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.common.constant.LazymanErrCode;
import org.lazyman.boot.common.constant.MenuType;
import org.lazyman.boot.sys.dto.*;
import org.lazyman.boot.sys.entity.SysMenu;
import org.lazyman.boot.sys.entity.SysPost;
import org.lazyman.boot.sys.entity.SysRole;
import org.lazyman.boot.sys.entity.SysUser;
import org.lazyman.boot.sys.mapper.SysUserMapper;
import org.lazyman.boot.sys.service.*;
import org.lazyman.boot.sys.vo.*;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Resource
    private ISysRoleService iSysRoleService;
    @Resource
    private ISysMenuService iSysMenuService;
    @Resource
    private ISysUserRoleService iSysUserRoleService;
    @Resource
    private ISysRoleMenuService iSysRoleMenuService;
    @Resource
    private ISysDeptService iSysDeptService;
    @Resource
    private ISysUserPostService iSysUserPostService;
    @Resource
    private ISysPostService iSysPostService;

    @Override
    public Boolean exists(SysUserFormDTO sysUserFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysUser>query().lambda()
                .eq(StrUtil.isNotBlank(sysUserFormDTO.getUsername()), SysUser::getUsername, sysUserFormDTO.getUsername())
                .eq(ObjectUtil.isNotEmpty(sysUserFormDTO.getDeptId()), SysUser::getDeptId, sysUserFormDTO.getDeptId())
                .ne(ObjectUtil.isNotNull(sysUserFormDTO.getId()), SysUser::getId, sysUserFormDTO.getId())));
    }

    @Override
    public SysUser existsById(Long id) {
        SysUser sysUser = getById(id);
        if (ObjectUtil.isNull(sysUser)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysUserFormDTO sysUserFormDTO) {
        if (exists(sysUserFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysUser sysUser = BeanUtil.copyProperties(sysUserFormDTO, SysUser.class);
        sysUser.setPassword(BCrypt.hashpw(sysUserFormDTO.getPassword(), BCrypt.gensalt()));
        save(sysUser);
        iSysUserRoleService.createUserRole(sysUser.getId(), sysUserFormDTO.getRoleIds());
        iSysUserPostService.createUserPost(sysUser.getId(), sysUserFormDTO.getPostIds());
        return sysUser.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysUserFormDTO sysUserFormDTO) {
        existsById(sysUserFormDTO.getId());
        if (exists(sysUserFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysUser sysUser = BeanUtil.copyProperties(sysUserFormDTO, SysUser.class);
        sysUser.setPassword(null);
        iSysUserRoleService.createUserRole(sysUser.getId(), sysUserFormDTO.getRoleIds());
        iSysUserPostService.createUserPost(sysUser.getId(), sysUserFormDTO.getPostIds());
        return updateById(sysUser);
    }

    @Override
    public SysUserDetailVO getDetail(Long id) {
        SysUser sysUser = existsById(id);
        SysUserVO sysUserVO = BeanUtil.copyProperties(sysUser, SysUserVO.class);
        SysUserDetailVO sysUserDetailVO = new SysUserDetailVO();
        sysUserDetailVO.setUser(sysUserVO);
        sysUserDetailVO.setDept(iSysDeptService.getDetail(sysUser.getDeptId()));
        sysUserDetailVO.setPosts(listUserPosts(sysUser.getId()));
        sysUserDetailVO.setRoles(listUserRoles(sysUser.getId()));
        return sysUserDetailVO;
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysUserVO> listByPage(SysUserQueryDTO sysUserQueryDTO) {
        List<Long> deptIds = iSysDeptService.listByParentId(sysUserQueryDTO.getDeptId());
        Page<SysUser> sysUserPage = new Page<>(sysUserQueryDTO.getPageNo(), sysUserQueryDTO.getPageSize());
        IPage<SysUserVO> sysUserVOIPage = page(sysUserPage, Wrappers.<SysUser>query().lambda()
                .like(StrUtil.isNotBlank(sysUserQueryDTO.getUsername()), SysUser::getUsername, sysUserQueryDTO.getUsername())
                .like(StrUtil.isNotBlank(sysUserQueryDTO.getMobile()), SysUser::getMobile, sysUserQueryDTO.getMobile())
                .in(CollectionUtil.isNotEmpty(deptIds), SysUser::getDeptId, deptIds)
                .eq(ObjectUtil.isNotEmpty(sysUserQueryDTO.getState()), SysUser::getPostId, sysUserQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysUserQueryDTO.getBeginTime(), sysUserQueryDTO.getEndTime()), SysUser::getCreateTime, sysUserQueryDTO.getBeginTime(), sysUserQueryDTO.getEndTime())
                .orderByDesc(SysUser::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysUserVO.class));
        return new PageVO<>(sysUserVOIPage.getTotal(), sysUserVOIPage.getRecords());
    }

    @Override
    public List<SysUserVO> listSelectOptions(String keyword) {
        List<SysUserVO> selectOptionVOList = new ArrayList<>();
        List<SysUser> sysUserList = list(Wrappers.<SysUser>query().lambda()
                .like(StrUtil.isNotBlank(keyword), SysUser::getNickname, keyword)
                .orderByAsc(SysUser::getCreateTime));
        if (CollectionUtil.isEmpty(sysUserList)) {
            return selectOptionVOList;
        }
        return sysUserList.stream().map(v -> BeanUtil.copyProperties(v, SysUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysUser>update().lambda().eq(SysUser::getId, stateActionDTO.getId())
                .eq(SysUser::getState, !stateActionDTO.getState())
                .set(SysUser::getState, stateActionDTO.getState()));
    }

    @Override
    public Boolean resetSysUserPwd(SysUserResetPwdDTO sysUserResetPwdDTO) {
        existsById(sysUserResetPwdDTO.getId());
        return updateByWrapper(Wrappers.<SysUser>update().lambda().eq(SysUser::getId, sysUserResetPwdDTO.getId())
                .set(SysUser::getPassword, BCrypt.hashpw(sysUserResetPwdDTO.getPassword(), BCrypt.gensalt())));
    }

    @Override
    public LoginUserInfoVO getUserInfo(Long userId) {
        SysUserDetailVO sysUserDetailVO = getDetail(userId);
        LoginUserInfoVO loginUserInfoVO = new LoginUserInfoVO();
        loginUserInfoVO.setUserInfo(sysUserDetailVO.getUser());
        if (CollectionUtil.isNotEmpty(sysUserDetailVO.getRoles())) {
            loginUserInfoVO.setRoles(sysUserDetailVO.getRoles().stream().map(SysRoleVO::getRoleCode).collect(Collectors.toList()));
        }
        //超管拥有所有权限
        if (sysUserDetailVO.getUser().getIsAdmin()) {
            loginUserInfoVO.setPermissions(Arrays.asList(LazymanConstant.Permission.ADMIN));
        } else {
            List<SysMenuVO> sysMenuVOList = listUserPermissions(userId);
            if (CollectionUtil.isNotEmpty(sysMenuVOList)) {
                loginUserInfoVO.setPermissions(sysMenuVOList.stream().map(SysMenuVO::getMenuCode).collect(Collectors.toList()));
            } else {
                loginUserInfoVO.setPermissions(new ArrayList<>());
            }
        }
        return loginUserInfoVO;
    }

    @Override
    public List<SysPostVO> listUserPosts(Long id) {
        List<SysPostVO> sysPostVOList = new ArrayList<>();
        List<Long> roleIds = iSysUserPostService.listUserPostIds(id);
        if (CollectionUtil.isEmpty(roleIds)) {
            return sysPostVOList;
        }
        List<SysPost> sysPostList = iSysPostService.list(Wrappers.<SysPost>query().lambda().in(SysPost::getId, roleIds).eq(SysPost::getState, true));
        if (CollectionUtil.isEmpty(sysPostList)) {
            return sysPostVOList;
        }
        sysPostVOList = sysPostList.stream().map(v -> BeanUtil.copyProperties(v, SysPostVO.class)).collect(Collectors.toList());
        return sysPostVOList;
    }

    @Override
    public List<SysRoleVO> listUserRoles(Long id) {
        List<SysRoleVO> sysRoleVOList = new ArrayList<>();
        List<Long> roleIds = iSysUserRoleService.listUserRoleIds(id);
        if (CollectionUtil.isEmpty(roleIds)) {
            return sysRoleVOList;
        }
        List<SysRole> sysRoleList = iSysRoleService.list(Wrappers.<SysRole>query().lambda().in(SysRole::getId, roleIds).eq(SysRole::getState, true));
        if (CollectionUtil.isEmpty(sysRoleList)) {
            return sysRoleVOList;
        }
        sysRoleVOList = sysRoleList.stream().map(v -> BeanUtil.copyProperties(v, SysRoleVO.class)).collect(Collectors.toList());
        return sysRoleVOList;
    }

    @Override
    public List<SysMenuVO> listUserPermissions(Long id) {
        List<SysMenuVO> sysMenuVOList = new ArrayList<>();
        List<SysRoleVO> sysRoleVOList = listUserRoles(id);
        if (CollectionUtil.isEmpty(sysRoleVOList)) {
            return sysMenuVOList;
        }
        List<Long> menuIds = iSysRoleMenuService.listRoleMenuIds(sysRoleVOList.stream().map(SysRoleVO::getId).collect(Collectors.toList()));
        if (CollectionUtil.isEmpty(menuIds)) {
            return sysMenuVOList;
        }
        List<SysMenu> sysMenuList = iSysMenuService.list(Wrappers.<SysMenu>query().lambda().in(SysMenu::getId, menuIds)
                .eq(SysMenu::getMenuType, MenuType.BUTTON.getCode())
                .eq(SysMenu::getState, true)
        );
        if (CollectionUtil.isEmpty(sysMenuList)) {
            return sysMenuVOList;
        }
        sysMenuVOList = sysMenuList.stream().map(v -> BeanUtil.copyProperties(v, SysMenuVO.class)).collect(Collectors.toList());
        return sysMenuVOList;
    }

    @Override
    public List<VueRouterTreeVO> listUserRouterByTree(Long id) {
        List<VueRouterTreeVO> vueRouterTreeVOList = new ArrayList<>();
        List<SysRoleVO> sysRoleVOList = listUserRoles(id);
        if (CollectionUtil.isEmpty(sysRoleVOList)) {
            return vueRouterTreeVOList;
        }
        vueRouterTreeVOList = iSysMenuService.listVueRouterByTree(sysRoleVOList.stream().map(SysRoleVO::getId).collect(Collectors.toList()));
        return vueRouterTreeVOList;
    }

    @Override
    public Boolean updateSysUserProfile(SysUserProfileFormDTO sysUserProfileFormDTO) {
        existsById(sysUserProfileFormDTO.getId());
        SysUser sysUser = BeanUtil.copyProperties(sysUserProfileFormDTO, SysUser.class);
        return updateById(sysUser);
    }

    @Override
    public Boolean updateSysUserPwd(SysUserUpdatePwdDTO sysUserUpdatePwdDTO) {
        SysUser sysUser = existsById(sysUserUpdatePwdDTO.getId());
        if (!BCrypt.checkpw(sysUserUpdatePwdDTO.getOldPassword(), sysUser.getPassword())) {
            throw new BizException(LazymanErrCode.OLD_PWD_ERROR);
        }
        return updateByWrapper(Wrappers.<SysUser>update().lambda().eq(SysUser::getId, sysUser.getId())
                .set(SysUser::getPassword, BCrypt.hashpw(sysUserUpdatePwdDTO.getNewPassword(), BCrypt.gensalt())));
    }
}
