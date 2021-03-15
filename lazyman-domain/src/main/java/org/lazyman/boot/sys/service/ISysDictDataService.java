package org.lazyman.boot.sys.service;

import org.lazyman.boot.sys.dto.SysDictDataFormDTO;
import org.lazyman.boot.sys.dto.SysDictDataQueryDTO;
import org.lazyman.boot.sys.entity.SysDictData;
import org.lazyman.boot.sys.vo.SysDictDataVO;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;

/**
 * <p>
 * 系统字典数据 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
public interface ISysDictDataService extends BaseService<SysDictData> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param sysDictDataFormDTO
     * @return
     */
    Boolean exists(SysDictDataFormDTO sysDictDataFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SysDictData existsById(Long id);

    /**
     * 新增
     *
     * @param sysDictDataFormDTO
     * @return
     */
    Long save(SysDictDataFormDTO sysDictDataFormDTO);

    /**
     * 编辑
     *
     * @param sysDictDataFormDTO
     * @return
     */
    Boolean edit(SysDictDataFormDTO sysDictDataFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SysDictDataVO getDetail(Long id);

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
     * @param sysDictDataQueryDTO
     * @return
     */
    PageVO<SysDictDataVO> listByPage(SysDictDataQueryDTO sysDictDataQueryDTO);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
