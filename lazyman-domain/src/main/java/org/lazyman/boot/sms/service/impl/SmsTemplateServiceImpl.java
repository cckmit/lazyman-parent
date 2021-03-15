package org.lazyman.boot.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sms.dto.SmsTemplateFormDTO;
import org.lazyman.boot.sms.dto.SmsTemplateQueryDTO;
import org.lazyman.boot.sms.entity.SmsTemplate;
import org.lazyman.boot.sms.mapper.SmsTemplateMapper;
import org.lazyman.boot.sms.service.ISmsTemplateService;
import org.lazyman.boot.sms.vo.SmsTemplateVO;
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
 * 短信模板 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Service
public class SmsTemplateServiceImpl extends BaseServiceImpl<SmsTemplateMapper, SmsTemplate> implements ISmsTemplateService {
    @Override
    public Boolean exists(SmsTemplateFormDTO smsTemplateFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SmsTemplate>query().lambda()
                .eq(ObjectUtil.isNotEmpty(smsTemplateFormDTO.getSmsType()), SmsTemplate::getSmsType, smsTemplateFormDTO.getSmsType())
                .eq(ObjectUtil.isNotEmpty(smsTemplateFormDTO.getCategory()), SmsTemplate::getCategory, smsTemplateFormDTO.getCategory())
                .ne(ObjectUtil.isNotNull(smsTemplateFormDTO.getId()), SmsTemplate::getId, smsTemplateFormDTO.getId())));
    }

    @Override
    public SmsTemplate existsById(Long id) {
        SmsTemplate smsTemplate = getById(id);
        if (ObjectUtil.isNull(smsTemplate)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return smsTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SmsTemplateFormDTO smsTemplateFormDTO) {
        if (exists(smsTemplateFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SmsTemplate smsTemplate = BeanUtil.copyProperties(smsTemplateFormDTO, SmsTemplate.class);
        save(smsTemplate);
        return smsTemplate.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SmsTemplateFormDTO smsTemplateFormDTO) {
        existsById(smsTemplateFormDTO.getId());
        if (exists(smsTemplateFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(smsTemplateFormDTO, SmsTemplate.class));
    }

    @Override
    public SmsTemplateVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SmsTemplateVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SmsTemplateVO> listByPage(SmsTemplateQueryDTO smsTemplateQueryDTO) {
        Page<SmsTemplate> smsTemplatePage = new Page<>(smsTemplateQueryDTO.getPageNo(), smsTemplateQueryDTO.getPageSize());
        IPage<SmsTemplateVO> smsTemplateVOIPage = page(smsTemplatePage, Wrappers.<SmsTemplate>query().lambda()
                .eq(ObjectUtil.isNotEmpty(smsTemplateQueryDTO.getSmsType()), SmsTemplate::getSmsType, smsTemplateQueryDTO.getSmsType())
                .eq(ObjectUtil.isNotEmpty(smsTemplateQueryDTO.getCategory()), SmsTemplate::getCategory, smsTemplateQueryDTO.getCategory())
                .eq(ObjectUtil.isNotEmpty(smsTemplateQueryDTO.getState()), SmsTemplate::getState, smsTemplateQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(smsTemplateQueryDTO.getBeginTime(), smsTemplateQueryDTO.getEndTime()), SmsTemplate::getCreateTime, smsTemplateQueryDTO.getBeginTime(), smsTemplateQueryDTO.getEndTime())
                .orderByDesc(SmsTemplate::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SmsTemplateVO.class));
        return new PageVO<>(smsTemplateVOIPage.getTotal(), smsTemplateVOIPage.getRecords());
    }

    @Override
    public List<SmsTemplateVO> listSelectOptions(String keyword) {
        List<SmsTemplateVO> selectOptionVOList = new ArrayList<>();
        List<SmsTemplate> smsTemplateList = list(Wrappers.<SmsTemplate>query().lambda()
                //todo .like(StrUtil.isNotBlank(keyword), SmsTemplate::getNickname, keyword)
                .orderByAsc(SmsTemplate::getCreateTime));
        if (CollectionUtil.isEmpty(smsTemplateList)) {
            return selectOptionVOList;
        }
        return smsTemplateList.stream().map(v -> BeanUtil.copyProperties(v, SmsTemplateVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SmsTemplate>update().lambda().eq(SmsTemplate::getId, stateActionDTO.getId())
                .eq(SmsTemplate::getState, !stateActionDTO.getState())
                .set(SmsTemplate::getState, stateActionDTO.getState()));
    }
}
