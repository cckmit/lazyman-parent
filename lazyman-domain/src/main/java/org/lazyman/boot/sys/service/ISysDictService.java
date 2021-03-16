package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.sys.dto.SysDictFormDTO;
import org.lazyman.boot.sys.dto.SysDictQueryDTO;
import org.lazyman.boot.sys.entity.SysDict;
import org.lazyman.boot.sys.vo.SysDictDataVO;
import org.lazyman.boot.sys.vo.SysDictVO;

import java.util.List;

/**
 * <p>
 * 系统字典 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
public interface ISysDictService extends BaseService<SysDict> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysDictFormDTO
     * @return
     */
    Boolean exists(SysDictFormDTO sysDictFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysDict existsById(Long id);

    /**
     * 新增
     *
     * @param sysDictFormDTO
     * @return
     */
    Long save(SysDictFormDTO sysDictFormDTO);

    /**
     * 编辑
     *
     * @param sysDictFormDTO
     * @return
     */
    Boolean edit(SysDictFormDTO sysDictFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysDictVO getDetail(Long id);

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
     * @param sysDictQueryDTO
     * @return
     */
    PageVO<SysDictVO> listByPage(SysDictQueryDTO sysDictQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SysDictVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);

    /**
     * 查询字典数据列表
     *
     * @param dictType
     * @return
     */
    List<SysDictDataVO> listDictDatas(String dictType);
}
