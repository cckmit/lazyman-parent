package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sys.dto.SysDictFormDTO;
import org.lazyman.boot.sys.dto.SysDictQueryDTO;
import org.lazyman.boot.sys.entity.SysDict;
import org.lazyman.boot.sys.entity.SysDictData;
import org.lazyman.boot.sys.mapper.SysDictMapper;
import org.lazyman.boot.sys.service.ISysDictDataService;
import org.lazyman.boot.sys.service.ISysDictService;
import org.lazyman.boot.sys.vo.SysDictDataVO;
import org.lazyman.boot.sys.vo.SysDictVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统字典 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements ISysDictService {
    @Resource
    private ISysDictDataService iSysDictDataService;

    @Override
    public Boolean exists(SysDictFormDTO sysDictFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysDict>query().lambda()
                .eq(StrUtil.isNotBlank(sysDictFormDTO.getDictName()), SysDict::getDictName, sysDictFormDTO.getDictName())
                .eq(StrUtil.isNotBlank(sysDictFormDTO.getDictType()), SysDict::getDictType, sysDictFormDTO.getDictType())
                .ne(ObjectUtil.isNotNull(sysDictFormDTO.getId()), SysDict::getId, sysDictFormDTO.getId())));
    }

    @Override
    public SysDict existsById(Long id) {
        SysDict sysDict = getById(id);
        if (ObjectUtil.isNull(sysDict)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysDict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysDictFormDTO sysDictFormDTO) {
        if (exists(sysDictFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysDict sysDict = BeanUtil.copyProperties(sysDictFormDTO, SysDict.class);
        save(sysDict);
        return sysDict.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysDictFormDTO sysDictFormDTO) {
        SysDict sysDict = existsById(sysDictFormDTO.getId());
        if (exists(sysDictFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        if (!StrUtil.equals(sysDict.getDictType(), sysDictFormDTO.getDictType())) {
            iSysDictDataService.updateByWrapper(Wrappers.<SysDictData>update().lambda().eq(SysDictData::getDictType, sysDict.getDictType())
                    .set(SysDictData::getDictType, sysDictFormDTO.getDictType()));
        }
        return updateById(BeanUtil.copyProperties(sysDictFormDTO, SysDict.class));
    }

    @Override
    public SysDictVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysDictVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysDictVO> listByPage(SysDictQueryDTO sysDictQueryDTO) {
        Page<SysDict> sysDictPage = new Page<>(sysDictQueryDTO.getPageNo(), sysDictQueryDTO.getPageSize());
        IPage<SysDictVO> sysDictVOIPage = page(sysDictPage, Wrappers.<SysDict>query().lambda()
                .like(StrUtil.isNotBlank(sysDictQueryDTO.getDictName()), SysDict::getDictName, sysDictQueryDTO.getDictName())
                .like(StrUtil.isNotBlank(sysDictQueryDTO.getDictType()), SysDict::getDictType, sysDictQueryDTO.getDictType())
                .eq(ObjectUtil.isNotEmpty(sysDictQueryDTO.getState()), SysDict::getState, sysDictQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysDictQueryDTO.getBeginTime(), sysDictQueryDTO.getEndTime()), SysDict::getCreateTime, sysDictQueryDTO.getBeginTime(), sysDictQueryDTO.getEndTime())
                .orderByDesc(SysDict::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysDictVO.class));
        return new PageVO<>(sysDictVOIPage.getTotal(), sysDictVOIPage.getRecords());
    }

    @Override
    public List<SysDictVO> listSelectOptions(String keyword) {
        List<SysDictVO> selectOptionVOList = new ArrayList<>();
        List<SysDict> sysDictList = list(Wrappers.<SysDict>query().lambda()
                .like(StrUtil.isNotBlank(keyword), SysDict::getDictName, keyword)
                .orderByAsc(SysDict::getCreateTime));
        if (CollectionUtil.isEmpty(sysDictList)) {
            return selectOptionVOList;
        }
        return sysDictList.stream().map(v -> BeanUtil.copyProperties(v, SysDictVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysDict>update().lambda().eq(SysDict::getId, stateActionDTO.getId())
                .eq(SysDict::getState, !stateActionDTO.getState())
                .set(SysDict::getState, stateActionDTO.getState()));
    }

    @Override
    public List<SysDictDataVO> listDictDatas(String dictType) {
        List<SysDictData> sysDictDataList = iSysDictDataService.list(Wrappers.<SysDictData>query().lambda()
                .eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getState, true)
                .orderByAsc(SysDictData::getSort));
        if (CollectionUtil.isEmpty(sysDictDataList)) {
            return null;
        }
        return sysDictDataList.stream().map(v -> BeanUtil.copyProperties(v, SysDictDataVO.class)).collect(Collectors.toList());
    }
}
