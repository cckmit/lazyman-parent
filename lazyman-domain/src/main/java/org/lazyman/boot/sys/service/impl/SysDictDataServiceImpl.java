package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.sys.dto.SysDictDataFormDTO;
import org.lazyman.boot.sys.dto.SysDictDataQueryDTO;
import org.lazyman.boot.sys.entity.SysDictData;
import org.lazyman.boot.sys.mapper.SysDictDataMapper;
import org.lazyman.boot.sys.service.ISysDictDataService;
import org.lazyman.boot.sys.vo.SysDictDataVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * <p>
 * 系统字典数据 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {
    @Override
    public Boolean exists(SysDictDataFormDTO sysDictDataFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysDictData>query().lambda()
                .eq(StrUtil.isNotBlank(sysDictDataFormDTO.getDictType()), SysDictData::getDictType, sysDictDataFormDTO.getDictType())
                .eq(StrUtil.isNotBlank(sysDictDataFormDTO.getDictValue()), SysDictData::getDictValue, sysDictDataFormDTO.getDictValue())
                .ne(ObjectUtil.isNotNull(sysDictDataFormDTO.getId()), SysDictData::getId, sysDictDataFormDTO.getId())));
    }

    @Override
    public SysDictData existsById(Long id) {
        SysDictData sysDictData = getById(id);
        if (ObjectUtil.isNull(sysDictData)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysDictData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysDictDataFormDTO sysDictDataFormDTO) {
        if (exists(sysDictDataFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysDictData sysDictData = BeanUtil.copyProperties(sysDictDataFormDTO, SysDictData.class);
        save(sysDictData);
        return sysDictData.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysDictDataFormDTO sysDictDataFormDTO) {
        existsById(sysDictDataFormDTO.getId());
        if (exists(sysDictDataFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(sysDictDataFormDTO, SysDictData.class));
    }

    @Override
    public SysDictDataVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysDictDataVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysDictDataVO> listByPage(SysDictDataQueryDTO sysDictDataQueryDTO) {
        Page<SysDictData> sysDictDataPage = new Page<>(sysDictDataQueryDTO.getPageNo(), sysDictDataQueryDTO.getPageSize());
        IPage<SysDictDataVO> sysDictDataVOIPage = page(sysDictDataPage, Wrappers.<SysDictData>query().lambda()
                .like(StrUtil.isNotBlank(sysDictDataQueryDTO.getDictType()), SysDictData::getDictType, sysDictDataQueryDTO.getDictType())
                .like(StrUtil.isNotBlank(sysDictDataQueryDTO.getDictLabel()), SysDictData::getDictLabel, sysDictDataQueryDTO.getDictLabel())
                .eq(ObjectUtil.isNotEmpty(sysDictDataQueryDTO.getState()), SysDictData::getState, sysDictDataQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysDictDataQueryDTO.getBeginTime(), sysDictDataQueryDTO.getEndTime()), SysDictData::getCreateTime, sysDictDataQueryDTO.getBeginTime(), sysDictDataQueryDTO.getEndTime())
                .orderByAsc(SysDictData::getSort)).convert(v -> BeanUtil.copyProperties(v, SysDictDataVO.class));
        return new PageVO<>(sysDictDataVOIPage.getTotal(), sysDictDataVOIPage.getRecords());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysDictData>update().lambda().eq(SysDictData::getId, stateActionDTO.getId())
                .eq(SysDictData::getState, !stateActionDTO.getState())
                .set(SysDictData::getState, stateActionDTO.getState()));
    }
}
