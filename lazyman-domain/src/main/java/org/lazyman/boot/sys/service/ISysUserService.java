package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.common.auth.vo.LoginUserInfoVO;
import org.lazyman.boot.sys.dto.*;
import org.lazyman.boot.sys.entity.SysUser;
import org.lazyman.boot.sys.vo.*;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
public interface ISysUserService extends BaseService<SysUser> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysUserFormDTO
     * @return
     */
    Boolean exists(SysUserFormDTO sysUserFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysUser existsById(Long id);

    /**
     * 新增
     *
     * @param sysUserFormDTO
     * @return
     */
    Long save(SysUserFormDTO sysUserFormDTO);

    /**
     * 编辑
     *
     * @param sysUserFormDTO
     * @return
     */
    Boolean edit(SysUserFormDTO sysUserFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysUserDetailVO getDetail(Long id);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    Boolean delete(Long[] ids);

    /**
     * 分页查询
     *
     * @param sysUserQueryDTO
     * @return
     */
    PageVO<SysUserVO> listByPage(SysUserQueryDTO sysUserQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SysUserVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);

    /**
     * 重置用户密码
     *
     * @param sysUserResetPwdDTO
     * @return
     */
    Boolean resetSysUserPwd(SysUserResetPwdDTO sysUserResetPwdDTO);

    /**
     * 获取登录用户信息
     *
     * @param id
     * @return
     */
    LoginUserInfoVO getUserInfo(Long id);

    /**
     * 询用户岗位信息列表
     *
     * @param id
     * @return
     */
    List<SysPostVO> listUserPosts(Long id);

    /**
     * 查询用户角色信息列表
     *
     * @param id
     * @return
     */
    List<SysRoleVO> listUserRoles(Long id);

    /**
     * 查询用户权限信息列表
     *
     * @param id
     * @return
     */
    List<SysMenuVO> listUserPermissions(Long id);

    /**
     * 查询用户vue路由树
     *
     * @param id
     * @return
     */
    List<VueRouterTreeVO> listUserRouterByTree(Long id);

    /**
     * 编辑个人中心profile
     *
     * @param sysUserProfileFormDTO
     * @return
     */
    Boolean updateSysUserProfile(SysUserProfileFormDTO sysUserProfileFormDTO);

    /**
     * 修改用户密码
     *
     * @param sysUserUpdatePwdDTO
     * @return
     */
    Boolean updateSysUserPwd(SysUserUpdatePwdDTO sysUserUpdatePwdDTO);
}
