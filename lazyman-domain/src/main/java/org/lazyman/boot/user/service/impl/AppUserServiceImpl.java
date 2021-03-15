package org.lazyman.boot.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.user.dto.AppUserFormDTO;
import org.lazyman.boot.user.dto.AppUserQueryDTO;
import org.lazyman.boot.user.entity.AppUser;
import org.lazyman.boot.user.mapper.AppUserMapper;
import org.lazyman.boot.user.service.IAppUserService;
import org.lazyman.boot.user.vo.AppUserVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * APP用户 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUserMapper, AppUser> implements IAppUserService {
    @Override
    public AppUser existsById(Long id) {
        AppUser appBuyer = getById(id);
        if (ObjectUtil.isNull(appBuyer)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return appBuyer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(AppUserFormDTO appBuyerFormDTO) {
        existsById(appBuyerFormDTO.getId());
        return updateById(BeanUtil.copyProperties(appBuyerFormDTO, AppUser.class));
    }

    @Override
    public AppUserVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), AppUserVO.class);
    }

    @Override
    public PageVO<AppUserVO> listByPage(AppUserQueryDTO appBuyerQueryDTO) {
        Page<AppUser> appBuyerPage = new Page<>(appBuyerQueryDTO.getPageNo(), appBuyerQueryDTO.getPageSize());
        IPage<AppUserVO> appBuyerVOIPage = page(appBuyerPage, Wrappers.<AppUser>query().lambda()
                //todo query condition
                .eq(ObjectUtil.isNotEmpty(appBuyerQueryDTO.getState()), AppUser::getState, appBuyerQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(appBuyerQueryDTO.getBeginTime(), appBuyerQueryDTO.getEndTime()), AppUser::getCreateTime, appBuyerQueryDTO.getBeginTime(), appBuyerQueryDTO.getEndTime())
                .orderByDesc(AppUser::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, AppUserVO.class));
        return new PageVO<>(appBuyerVOIPage.getTotal(), appBuyerVOIPage.getRecords());
    }


    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<AppUser>update().lambda().eq(AppUser::getId, stateActionDTO.getId())
                .eq(AppUser::getState, !stateActionDTO.getState())
                .set(AppUser::getState, stateActionDTO.getState()));
    }
}
