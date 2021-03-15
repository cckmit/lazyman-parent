package org.lazyman.boot.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jodd.util.StringUtil;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.common.constant.LazymanErrCode;
import org.lazyman.boot.common.constant.SmsState;
import org.lazyman.boot.sms.bo.SmsMessageBO;
import org.lazyman.boot.sms.dto.SmsTaskFormDTO;
import org.lazyman.boot.sms.dto.SmsTaskQueryDTO;
import org.lazyman.boot.sms.entity.SmsChannel;
import org.lazyman.boot.sms.entity.SmsTask;
import org.lazyman.boot.sms.entity.SmsTemplate;
import org.lazyman.boot.sms.handler.SmsTaskHandler;
import org.lazyman.boot.sms.handler.SmsTaskHelper;
import org.lazyman.boot.sms.mapper.SmsTaskMapper;
import org.lazyman.boot.sms.service.ISmsChannelService;
import org.lazyman.boot.sms.service.ISmsTaskService;
import org.lazyman.boot.sms.service.ISmsTemplateService;
import org.lazyman.boot.sms.vo.SmsTaskVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.IDGeneratorUtils;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 短信任务表 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Service
public class SmsTaskServiceImpl extends BaseServiceImpl<SmsTaskMapper, SmsTask> implements ISmsTaskService {
    @Resource
    private ISmsTemplateService iSmsTemplateService;
    @Resource
    private ISmsChannelService iSmsChannelService;
    @Resource
    private SmsTaskHelper smsTaskHelper;
    @Resource
    private RedissonTemplate redissonTemplate;

    @Override
    public SmsTask existsById(Long id) {
        SmsTask smsTask = getById(id);
        if (ObjectUtil.isNull(smsTask)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return smsTask;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAndSend(SmsTaskFormDTO smsTaskFormDTO) {
        SmsTemplate smsTemplate = iSmsTemplateService.getOne(Wrappers.<SmsTemplate>query().lambda().eq(SmsTemplate::getCategory, smsTaskFormDTO.getTemplateCategory()));
        if (Objects.isNull(smsTemplate)) {
            throw new BizException(LazymanErrCode.SMS_TEMPLATE_NOT_FOUND);
        }
        String[] mobiles = StringUtil.split(smsTaskFormDTO.getMobiles(), StringPool.COMMA);
        String content = smsTaskHelper.getContent(smsTemplate.getContent(), smsTaskFormDTO.getTemplateParams());
        Long batchId = IDGeneratorUtils.getInstance().nextId();
        SmsTask smsTask = null;
        List<SmsTask> iSmsTaskList = new ArrayList<>();
        if (mobiles != null && mobiles.length > 0) {
            for (String mobile : mobiles) {
                smsTask = new SmsTask();
                smsTask.setBatchId(batchId);
                smsTask.setSmsType(smsTemplate.getSmsType());
                smsTask.setTemplateCategory(smsTemplate.getCategory());
                smsTask.setContent(content);
                smsTask.setState(SmsState.TO_SEND.getCode());
                smsTask.setMobile(mobile);
                iSmsTaskList.add(smsTask);
            }
        }
        saveBatch(iSmsTaskList);
        SmsMessageBO smsMessageBO = new SmsMessageBO();
        smsMessageBO.setSmsType(smsTemplate.getSmsType());
        smsMessageBO.setBatchId(batchId);
        smsMessageBO.setMobiles(Arrays.asList(smsTaskFormDTO.getMobiles()));
        smsMessageBO.setContent(content);
        redissonTemplate.convertAndSend(LazymanConstant.RedisQueue.QUEUE_SMS_SEND, smsMessageBO);
        return smsTask.getId();
    }

    @Override
    public void handleSmsTask(SmsMessageBO smsMessageBO) {
        List<SmsChannel> sysSmsChannelList = iSmsChannelService.list(Wrappers.<SmsChannel>query().lambda()
                .eq(SmsChannel::getSmsType, smsMessageBO.getSmsType())
                .eq(SmsChannel::getState, true)
                .orderByAsc(SmsChannel::getPriority)
        );
        if (CollectionUtils.isEmpty(sysSmsChannelList)) {
            return;
        }
        boolean success = false;
        SmsChannel currentSmsChannel = null;
        for (SmsChannel sysSmsChannel : sysSmsChannelList) {
            currentSmsChannel = sysSmsChannel;
            SmsTaskHandler smsTaskHandler = smsTaskHelper.lookupHandler(sysSmsChannel.getVendorCode());
            if (Objects.isNull(smsTaskHandler)) {
                continue;
            }
            success = smsTaskHandler.send(smsMessageBO, sysSmsChannel);
            if (success) {
                break;
            }
        }
        LambdaUpdateWrapper<SmsTask> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SmsTask::getSendTime, DateUtil.date().toJdkDate());
        if (success) {
            lambdaUpdateWrapper.eq(SmsTask::getBatchId, smsMessageBO.getBatchId()).set(SmsTask::getState, SmsState.SEND_SUCCESS.getCode())
                    .set(SmsTask::getChannelId, currentSmsChannel.getId());
        } else {
            lambdaUpdateWrapper.eq(SmsTask::getBatchId, smsMessageBO.getBatchId()).set(SmsTask::getState, SmsState.SEND_FAILURE.getCode());
        }
        updateByWrapper(lambdaUpdateWrapper);
    }

    @Override
    public SmsTaskVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SmsTaskVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SmsTaskVO> listByPage(SmsTaskQueryDTO smsTaskQueryDTO) {
        Page<SmsTask> smsTaskPage = new Page<>(smsTaskQueryDTO.getPageNo(), smsTaskQueryDTO.getPageSize());
        IPage<SmsTaskVO> smsTaskVOIPage = page(smsTaskPage, Wrappers.<SmsTask>query().lambda()
                .eq(ObjectUtil.isNotEmpty(smsTaskQueryDTO.getTemplateCategory()), SmsTask::getTemplateCategory, smsTaskQueryDTO.getTemplateCategory())
                .eq(StrUtil.isNotBlank(smsTaskQueryDTO.getMobile()), SmsTask::getMobile, smsTaskQueryDTO.getMobile())
                .eq(ObjectUtil.isNotEmpty(smsTaskQueryDTO.getState()), SmsTask::getState, smsTaskQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(smsTaskQueryDTO.getBeginTime(), smsTaskQueryDTO.getEndTime()), SmsTask::getCreateTime, smsTaskQueryDTO.getBeginTime(), smsTaskQueryDTO.getEndTime())
                .orderByDesc(SmsTask::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SmsTaskVO.class));
        return new PageVO<>(smsTaskVOIPage.getTotal(), smsTaskVOIPage.getRecords());
    }
}
