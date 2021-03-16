package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.sys.dto.SysDeptFormDTO;
import org.lazyman.boot.sys.dto.SysDeptQueryDTO;
import org.lazyman.boot.sys.entity.SysDept;
import org.lazyman.boot.sys.vo.SysDeptTreeVO;
import org.lazyman.boot.sys.vo.SysDeptVO;
import org.lazyman.boot.sys.vo.VueElTreeVO;

import java.util.List;

/**
 * <p>
 * 系统部门 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
public interface ISysDeptService extends BaseService<SysDept> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysDeptFormDTO
     * @return
     */
    Boolean exists(SysDeptFormDTO sysDeptFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysDept existsById(Long id);

    /**
     * 新增
     *
     * @param sysDeptFormDTO
     * @return
     */
    Long save(SysDeptFormDTO sysDeptFormDTO);

    /**
     * 编辑
     *
     * @param sysDeptFormDTO
     * @return
     */
    Boolean edit(SysDeptFormDTO sysDeptFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysDeptVO getDetail(Long id);

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
     * @param sysDeptQueryDTO
     * @return
     */
    PageVO<SysDeptVO> listByPage(SysDeptQueryDTO sysDeptQueryDTO);


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
     * @param sysDeptQueryDTO
     * @return
     */
    List<SysDeptTreeVO> listByTree(SysDeptQueryDTO sysDeptQueryDTO);

    /**
     * 查询vue el菜单权限树列表
     *
     * @return
     */
    List<VueElTreeVO> listVueElDeptByTree();

    /**
     * "根据父ID查询子节点列表
     *
     * @param parentId
     * @return
     */
    List<SysDeptVO> listChildrensByParentId(Long parentId);

    /**
     * 根据父ID遍历所有节点ID集合
     *
     * @param parentId
     * @return
     */
    List<Long> listByParentId(Long parentId);
}
