package org.lazyman.boot.sys.service;

import org.lazyman.boot.sys.dto.SysNoticeFormDTO;
import org.lazyman.boot.sys.dto.SysNoticeQueryDTO;
import org.lazyman.boot.sys.entity.SysNotice;
import org.lazyman.boot.sys.vo.SysNoticeVO;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 系统通知公告 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
public interface ISysNoticeService extends BaseService<SysNotice> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysNoticeFormDTO
     * @return
     */
    Boolean exists(SysNoticeFormDTO sysNoticeFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysNotice existsById(Long id);

    /**
     * 新增
     *
     * @param sysNoticeFormDTO
     * @return
     */
    Long save(SysNoticeFormDTO sysNoticeFormDTO);

    /**
     * 编辑
     *
     * @param sysNoticeFormDTO
     * @return
     */
    Boolean edit(SysNoticeFormDTO sysNoticeFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysNoticeVO getDetail(Long id);

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
     * @param sysNoticeQueryDTO
     * @return
     */
    PageVO<SysNoticeVO> listByPage(SysNoticeQueryDTO sysNoticeQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SysNoticeVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
