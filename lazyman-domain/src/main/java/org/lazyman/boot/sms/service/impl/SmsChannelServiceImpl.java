package org.lazyman.boot.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sms.dto.SmsChannelFormDTO;
import org.lazyman.boot.sms.dto.SmsChannelQueryDTO;
import org.lazyman.boot.sms.entity.SmsChannel;
import org.lazyman.boot.sms.mapper.SmsChannelMapper;
import org.lazyman.boot.sms.service.ISmsChannelService;
import org.lazyman.boot.sms.vo.SmsChannelVO;
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
 * 短信通道 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Service
public class SmsChannelServiceImpl extends BaseServiceImpl<SmsChannelMapper, SmsChannel> implements ISmsChannelService {
    @Override
    public Boolean exists(SmsChannelFormDTO smsChannelFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SmsChannel>query().lambda()
                .eq(StrUtil.isNotBlank(smsChannelFormDTO.getChannelName()), SmsChannel::getChannelName, smsChannelFormDTO.getChannelName())
                .eq(StrUtil.isNotBlank(smsChannelFormDTO.getVendorCode()), SmsChannel::getVendorCode, smsChannelFormDTO.getVendorCode())
                .eq(ObjectUtil.isNotEmpty(smsChannelFormDTO.getSmsType()), SmsChannel::getSmsType, smsChannelFormDTO.getSmsType())
                .ne(ObjectUtil.isNotNull(smsChannelFormDTO.getId()), SmsChannel::getId, smsChannelFormDTO.getId())));
    }

    @Override
    public SmsChannel existsById(Long id) {
        SmsChannel smsChannel = getById(id);
        if (ObjectUtil.isNull(smsChannel)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return smsChannel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SmsChannelFormDTO smsChannelFormDTO) {
        if (exists(smsChannelFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SmsChannel smsChannel = BeanUtil.copyProperties(smsChannelFormDTO, SmsChannel.class);
        save(smsChannel);
        return smsChannel.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SmsChannelFormDTO smsChannelFormDTO) {
        existsById(smsChannelFormDTO.getId());
        if (exists(smsChannelFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(smsChannelFormDTO, SmsChannel.class));
    }

    @Override
    public SmsChannelVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SmsChannelVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SmsChannelVO> listByPage(SmsChannelQueryDTO smsChannelQueryDTO) {
        Page<SmsChannel> smsChannelPage = new Page<>(smsChannelQueryDTO.getPageNo(), smsChannelQueryDTO.getPageSize());
        IPage<SmsChannelVO> smsChannelVOIPage = page(smsChannelPage, Wrappers.<SmsChannel>query().lambda()
                .like(StrUtil.isNotBlank(smsChannelQueryDTO.getChannelName()), SmsChannel::getChannelName, smsChannelQueryDTO.getChannelName())
                .eq(StrUtil.isNotBlank(smsChannelQueryDTO.getVendorCode()), SmsChannel::getVendorCode, smsChannelQueryDTO.getVendorCode())
                .eq(ObjectUtil.isNotEmpty(smsChannelQueryDTO.getSmsType()), SmsChannel::getSmsType, smsChannelQueryDTO.getSmsType())
                .eq(ObjectUtil.isNotEmpty(smsChannelQueryDTO.getState()), SmsChannel::getState, smsChannelQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(smsChannelQueryDTO.getBeginTime(), smsChannelQueryDTO.getEndTime()), SmsChannel::getCreateTime, smsChannelQueryDTO.getBeginTime(), smsChannelQueryDTO.getEndTime())
                .orderByDesc(SmsChannel::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SmsChannelVO.class));
        return new PageVO<>(smsChannelVOIPage.getTotal(), smsChannelVOIPage.getRecords());
    }

    @Override
    public List<SmsChannelVO> listSelectOptions(String keyword) {
        List<SmsChannelVO> selectOptionVOList = new ArrayList<>();
        List<SmsChannel> smsChannelList = list(Wrappers.<SmsChannel>query().lambda()
                .like(StrUtil.isNotBlank(keyword), SmsChannel::getChannelName, keyword)
                .orderByAsc(SmsChannel::getCreateTime));
        if (CollectionUtil.isEmpty(smsChannelList)) {
            return selectOptionVOList;
        }
        return smsChannelList.stream().map(v -> BeanUtil.copyProperties(v, SmsChannelVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SmsChannel>update().lambda().eq(SmsChannel::getId, stateActionDTO.getId())
                .eq(SmsChannel::getState, !stateActionDTO.getState())
                .set(SmsChannel::getState, stateActionDTO.getState()));
    }
}
