package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.sys.dto.SysConfigFormDTO;
import org.lazyman.boot.sys.dto.SysConfigQueryDTO;
import org.lazyman.boot.sys.entity.SysConfig;
import org.lazyman.boot.sys.vo.SysConfigVO;

import java.util.List;

/**
 * <p>
 * 系统配置 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
public interface ISysConfigService extends BaseService<SysConfig> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysConfigFormDTO
     * @return
     */
    Boolean exists(SysConfigFormDTO sysConfigFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysConfig existsById(Long id);

    /**
     * 新增
     *
     * @param sysConfigFormDTO
     * @return
     */
    Long save(SysConfigFormDTO sysConfigFormDTO);

    /**
     * 编辑
     *
     * @param sysConfigFormDTO
     * @return
     */
    Boolean edit(SysConfigFormDTO sysConfigFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysConfigVO getDetail(Long id);

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
     * @param sysConfigQueryDTO
     * @return
     */
    PageVO<SysConfigVO> listByPage(SysConfigQueryDTO sysConfigQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SysConfigVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
