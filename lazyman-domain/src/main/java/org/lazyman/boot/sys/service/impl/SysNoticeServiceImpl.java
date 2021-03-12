package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sys.dto.SysNoticeFormDTO;
import org.lazyman.boot.sys.dto.SysNoticeQueryDTO;
import org.lazyman.boot.sys.entity.SysNotice;
import org.lazyman.boot.sys.mapper.SysNoticeMapper;
import org.lazyman.boot.sys.service.ISysNoticeService;
import org.lazyman.boot.sys.vo.SysNoticeVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.service.impl.BaseServiceImpl;
import org.lazyman.core.base.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统通知公告 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {
    @Override
    public Boolean exists(SysNoticeFormDTO sysNoticeFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysNotice>query().lambda()
                .eq(ObjectUtil.isNotEmpty(sysNoticeFormDTO.getNoticeType()), SysNotice::getNoticeType, sysNoticeFormDTO.getNoticeType())
                .eq(StrUtil.isNotBlank(sysNoticeFormDTO.getNoticeTitle()), SysNotice::getNoticeTitle, sysNoticeFormDTO.getNoticeTitle())
                .eq(StrUtil.isNotBlank(sysNoticeFormDTO.getNoticeContent()), SysNotice::getNoticeContent, sysNoticeFormDTO.getNoticeContent())
                .ne(ObjectUtil.isNotNull(sysNoticeFormDTO.getId()), SysNotice::getId, sysNoticeFormDTO.getId())));
    }

    @Override
    public SysNotice existsById(Long id) {
        SysNotice sysNotice = getById(id);
        if (ObjectUtil.isNull(sysNotice)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysNotice;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysNoticeFormDTO sysNoticeFormDTO) {
        if (exists(sysNoticeFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysNotice sysNotice = BeanUtil.copyProperties(sysNoticeFormDTO, SysNotice.class);
        save(sysNotice);
        return sysNotice.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysNoticeFormDTO sysNoticeFormDTO) {
        existsById(sysNoticeFormDTO.getId());
        if (exists(sysNoticeFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(sysNoticeFormDTO, SysNotice.class));
    }

    @Override
    public SysNoticeVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysNoticeVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysNoticeVO> listByPage(SysNoticeQueryDTO sysNoticeQueryDTO) {
        Page<SysNotice> sysNoticePage = new Page<>(sysNoticeQueryDTO.getPageNo(), sysNoticeQueryDTO.getPageSize());
        IPage<SysNoticeVO> sysNoticeVOIPage = page(sysNoticePage, Wrappers.<SysNotice>query().lambda()
                .like(StrUtil.isNotBlank(sysNoticeQueryDTO.getNoticeTitle()), SysNotice::getNoticeTitle, sysNoticeQueryDTO.getNoticeTitle())
                .eq(ObjectUtil.isNotEmpty(sysNoticeQueryDTO.getNoticeType()), SysNotice::getNoticeType, sysNoticeQueryDTO.getNoticeType())
                .eq(ObjectUtil.isNotEmpty(sysNoticeQueryDTO.getState()), SysNotice::getState, sysNoticeQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysNoticeQueryDTO.getBeginTime(), sysNoticeQueryDTO.getEndTime()), SysNotice::getCreateTime, sysNoticeQueryDTO.getBeginTime(), sysNoticeQueryDTO.getEndTime())
                .orderByDesc(SysNotice::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysNoticeVO.class));
        return new PageVO<>(sysNoticeVOIPage.getTotal(), sysNoticeVOIPage.getRecords());
    }

    @Override
    public List<SysNoticeVO> listSelectOptions(String keyword) {
        List<SysNoticeVO> selectOptionVOList = new ArrayList<>();
        List<SysNotice> sysNoticeList = list(Wrappers.<SysNotice>query().lambda()
                .like(StrUtil.isNotBlank(keyword), SysNotice::getNoticeTitle, keyword)
                .orderByAsc(SysNotice::getCreateTime));
        if (CollectionUtil.isEmpty(sysNoticeList)) {
            return selectOptionVOList;
        }
        return sysNoticeList.stream().map(v -> BeanUtil.copyProperties(v, SysNoticeVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysNotice>update().lambda().eq(SysNotice::getId, stateActionDTO.getId())
                .eq(SysNotice::getState, !stateActionDTO.getState())
                .set(SysNotice::getState, stateActionDTO.getState()));
    }
}
