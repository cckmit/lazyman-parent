package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.sys.dto.SysMenuFormDTO;
import org.lazyman.boot.sys.dto.SysMenuQueryDTO;
import org.lazyman.boot.sys.entity.SysMenu;
import org.lazyman.boot.sys.vo.SysMenuTreeVO;
import org.lazyman.boot.sys.vo.SysMenuVO;
import org.lazyman.boot.sys.vo.VueElTreeVO;
import org.lazyman.boot.sys.vo.VueRouterTreeVO;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
public interface ISysMenuService extends BaseService<SysMenu> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysMenuFormDTO
     * @return
     */
    Boolean exists(SysMenuFormDTO sysMenuFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysMenu existsById(Long id);

    /**
     * 新增
     *
     * @param sysMenuFormDTO
     * @return
     */
    Long save(SysMenuFormDTO sysMenuFormDTO);

    /**
     * 编辑
     *
     * @param sysMenuFormDTO
     * @return
     */
    Boolean edit(SysMenuFormDTO sysMenuFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysMenuVO getDetail(Long id);

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
     * @param sysMenuQueryDTO
     * @return
     */
    PageVO<SysMenuVO> listByPage(SysMenuQueryDTO sysMenuQueryDTO);


    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);

    /**
     * 查询树列表
     *
     * @param sysMenuQueryDTO
     * @return
     */
    List<SysMenuTreeVO> listByTree(SysMenuQueryDTO sysMenuQueryDTO);

    /**
     * 查询vue路由树列表
     *
     * @param roles
     * @return
     */
    List<VueRouterTreeVO> listVueRouterByTree(List<Long> roles);

    /**
     * 查询vue el菜单权限树列表
     *
     * @return
     */
    List<VueElTreeVO> listVueElPermissionByTree();

    /**
     * "根据父ID查询子节点列表
     *
     * @param parentId
     * @return
     */
    List<SysMenuVO> listChildrensByParentId(Long parentId);
}
