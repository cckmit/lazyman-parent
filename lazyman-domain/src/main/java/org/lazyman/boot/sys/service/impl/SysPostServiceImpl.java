package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sys.dto.SysPostFormDTO;
import org.lazyman.boot.sys.dto.SysPostQueryDTO;
import org.lazyman.boot.sys.entity.SysPost;
import org.lazyman.boot.sys.mapper.SysPostMapper;
import org.lazyman.boot.sys.service.ISysPostService;
import org.lazyman.boot.sys.vo.SysPostVO;
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
 * 系统岗位 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost> implements ISysPostService {
    @Override
    public Boolean exists(SysPostFormDTO sysPostFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysPost>query().lambda()
                .eq(StrUtil.isNotBlank(sysPostFormDTO.getPostName()), SysPost::getPostName, sysPostFormDTO.getPostName())
                .eq(StrUtil.isNotBlank(sysPostFormDTO.getPostCode()), SysPost::getPostCode, sysPostFormDTO.getPostCode())
                .ne(ObjectUtil.isNotNull(sysPostFormDTO.getId()), SysPost::getId, sysPostFormDTO.getId())));
    }

    @Override
    public SysPost existsById(Long id) {
        SysPost sysPost = getById(id);
        if (ObjectUtil.isNull(sysPost)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysPost;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysPostFormDTO sysPostFormDTO) {
        if (exists(sysPostFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysPost sysPost = BeanUtil.copyProperties(sysPostFormDTO, SysPost.class);
        save(sysPost);
        return sysPost.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysPostFormDTO sysPostFormDTO) {
        existsById(sysPostFormDTO.getId());
        if (exists(sysPostFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(sysPostFormDTO, SysPost.class));
    }

    @Override
    public SysPostVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysPostVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysPostVO> listByPage(SysPostQueryDTO sysPostQueryDTO) {
        Page<SysPost> sysPostPage = new Page<>(sysPostQueryDTO.getPageNo(), sysPostQueryDTO.getPageSize());
        IPage<SysPostVO> sysPostVOIPage = page(sysPostPage, Wrappers.<SysPost>query().lambda()
                .like(StrUtil.isNotBlank(sysPostQueryDTO.getPostName()), SysPost::getPostName, sysPostQueryDTO.getPostName())
                .like(StrUtil.isNotBlank(sysPostQueryDTO.getPostCode()), SysPost::getPostCode, sysPostQueryDTO.getPostCode())
                .eq(ObjectUtil.isNotEmpty(sysPostQueryDTO.getState()), SysPost::getState, sysPostQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysPostQueryDTO.getBeginTime(), sysPostQueryDTO.getEndTime()), SysPost::getCreateTime, sysPostQueryDTO.getBeginTime(), sysPostQueryDTO.getEndTime())
                .orderByDesc(SysPost::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysPostVO.class));
        return new PageVO<>(sysPostVOIPage.getTotal(), sysPostVOIPage.getRecords());
    }

    @Override
    public List<SysPostVO> listSelectOptions(String keyword) {
        List<SysPostVO> selectOptionVOList = new ArrayList<>();
        List<SysPost> sysPostList = list(Wrappers.<SysPost>query().lambda()
                .like(StrUtil.isNotBlank(keyword), SysPost::getPostName, keyword)
                .orderByAsc(SysPost::getCreateTime));
        if (CollectionUtil.isEmpty(sysPostList)) {
            return selectOptionVOList;
        }
        return sysPostList.stream().map(v -> BeanUtil.copyProperties(v, SysPostVO.class)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysPost>update().lambda().eq(SysPost::getId, stateActionDTO.getId())
                .eq(SysPost::getState, !stateActionDTO.getState())
                .set(SysPost::getState, stateActionDTO.getState()));
    }
}
