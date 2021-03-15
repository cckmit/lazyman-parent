package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sys.dto.SysConfigFormDTO;
import org.lazyman.boot.sys.dto.SysConfigQueryDTO;
import org.lazyman.boot.sys.entity.SysConfig;
import org.lazyman.boot.sys.mapper.SysConfigMapper;
import org.lazyman.boot.sys.service.ISysConfigService;
import org.lazyman.boot.sys.vo.SysConfigVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统配置 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {
    @Override
    public Boolean exists(SysConfigFormDTO sysConfigFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysConfig>query().lambda()
                .eq(StrUtil.isNotBlank(sysConfigFormDTO.getConfigName()), SysConfig::getConfigName, sysConfigFormDTO.getConfigName())
                .eq(StrUtil.isNotBlank(sysConfigFormDTO.getConfigKey()), SysConfig::getConfigKey, sysConfigFormDTO.getConfigKey())
                .ne(ObjectUtil.isNotNull(sysConfigFormDTO.getId()), SysConfig::getId, sysConfigFormDTO.getId())));
    }

    @Override
    public SysConfig existsById(Long id) {
        SysConfig sysConfig = getById(id);
        if (ObjectUtil.isNull(sysConfig)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysConfigFormDTO sysConfigFormDTO) {
        if (exists(sysConfigFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysConfig sysConfig = BeanUtil.copyProperties(sysConfigFormDTO, SysConfig.class);
        save(sysConfig);
        return sysConfig.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysConfigFormDTO sysConfigFormDTO) {
        existsById(sysConfigFormDTO.getId());
        if (exists(sysConfigFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(sysConfigFormDTO, SysConfig.class));
    }

    @Override
    public SysConfigVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysConfigVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysConfigVO> listByPage(SysConfigQueryDTO sysConfigQueryDTO) {
        Page<SysConfig> sysConfigPage = new Page<>(sysConfigQueryDTO.getPageNo(), sysConfigQueryDTO.getPageSize());
        IPage<SysConfigVO> sysConfigVOIPage = page(sysConfigPage, Wrappers.<SysConfig>query().lambda()
                .like(StrUtil.isNotBlank(sysConfigQueryDTO.getConfigName()), SysConfig::getConfigName, sysConfigQueryDTO.getConfigName())
                .like(StrUtil.isNotBlank(sysConfigQueryDTO.getConfigKey()), SysConfig::getConfigKey, sysConfigQueryDTO.getConfigKey())
                .eq(ObjectUtil.isNotEmpty(sysConfigQueryDTO.getState()), SysConfig::getState, sysConfigQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysConfigQueryDTO.getBeginTime(), sysConfigQueryDTO.getEndTime()), SysConfig::getCreateTime, sysConfigQueryDTO.getBeginTime(), sysConfigQueryDTO.getEndTime())
                .orderByDesc(SysConfig::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysConfigVO.class));
        return new PageVO<>(sysConfigVOIPage.getTotal(), sysConfigVOIPage.getRecords());
    }

    @Override
    public List<SysConfigVO> listSelectOptions(String keyword) {
        List<SysConfigVO> selectOptionVOList = new ArrayList<>();
        List<SysConfig> sysConfigList = list(Wrappers.<SysConfig>query().lambda()
                .like(StrUtil.isNotBlank(keyword), SysConfig::getConfigName, keyword)
                .orderByAsc(SysConfig::getCreateTime));
        if (CollectionUtil.isEmpty(sysConfigList)) {
            return selectOptionVOList;
        }
        return sysConfigList.stream().map(v -> BeanUtil.copyProperties(v, SysConfigVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysConfig>update().lambda().eq(SysConfig::getId, stateActionDTO.getId())
                .eq(SysConfig::getState, !stateActionDTO.getState())
                .set(SysConfig::getState, stateActionDTO.getState()));
    }
}
