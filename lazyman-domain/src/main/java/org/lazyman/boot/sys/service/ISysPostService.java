package org.lazyman.boot.sys.service;

import org.lazyman.boot.sys.dto.SysPostFormDTO;
import org.lazyman.boot.sys.dto.SysPostQueryDTO;
import org.lazyman.boot.sys.entity.SysPost;
import org.lazyman.boot.sys.vo.SysPostVO;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.service.BaseService;
import org.lazyman.core.base.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 系统岗位 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
public interface ISysPostService extends BaseService<SysPost> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysPostFormDTO
     * @return
     */
    Boolean exists(SysPostFormDTO sysPostFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysPost existsById(Long id);

    /**
     * 新增
     *
     * @param sysPostFormDTO
     * @return
     */
    Long save(SysPostFormDTO sysPostFormDTO);

    /**
     * 编辑
     *
     * @param sysPostFormDTO
     * @return
     */
    Boolean edit(SysPostFormDTO sysPostFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysPostVO getDetail(Long id);

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
     * @param sysPostQueryDTO
     * @return
     */
    PageVO<SysPostVO> listByPage(SysPostQueryDTO sysPostQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SysPostVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
