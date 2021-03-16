package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.sys.dto.SysRoleDataScopeFormDTO;
import org.lazyman.boot.sys.dto.SysRoleFormDTO;
import org.lazyman.boot.sys.dto.SysRoleQueryDTO;
import org.lazyman.boot.sys.entity.SysRole;
import org.lazyman.boot.sys.vo.SysRoleVO;

import java.util.List;

/**
 * <p>
 * 系统角色 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
public interface ISysRoleService extends BaseService<SysRole> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysRoleFormDTO
     * @return
     */
    Boolean exists(SysRoleFormDTO sysRoleFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysRole existsById(Long id);

    /**
     * 新增
     *
     * @param sysRoleFormDTO
     * @return
     */
    Long save(SysRoleFormDTO sysRoleFormDTO);

    /**
     * 编辑
     *
     * @param sysRoleFormDTO
     * @return
     */
    Boolean edit(SysRoleFormDTO sysRoleFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysRoleVO getDetail(Long id);

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
     * @param sysRoleQueryDTO
     * @return
     */
    PageVO<SysRoleVO> listByPage(SysRoleQueryDTO sysRoleQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SysRoleVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);

    /**
     * 查询角色所有的菜单权限
     *
     * @param roleId
     * @return
     */
    List<Long> listRoleMenuPermissions(Long roleId);

    /**
     * 设置数据权限
     *
     * @param sysRoleDataScopeFormDTO
     * @return
     */
    Boolean setDataScope(SysRoleDataScopeFormDTO sysRoleDataScopeFormDTO);

    /**
     * 查询角色数据权限集合
     *
     * @param roleId
     * @return
     */
    List<Long> listRoleDataScope(Long roleId);
}
