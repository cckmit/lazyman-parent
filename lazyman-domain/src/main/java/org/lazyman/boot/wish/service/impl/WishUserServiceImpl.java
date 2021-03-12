package org.lazyman.boot.wish.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.wish.dto.WishUserFormDTO;
import org.lazyman.boot.wish.dto.WishUserQueryDTO;
import org.lazyman.boot.wish.entity.WishUser;
import org.lazyman.boot.wish.mapper.WishUserMapper;
import org.lazyman.boot.wish.service.IWishUserService;
import org.lazyman.boot.wish.vo.WishUserVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.service.impl.BaseServiceImpl;
import org.lazyman.core.base.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * wish买家 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
@Service
public class WishUserServiceImpl extends BaseServiceImpl<WishUserMapper, WishUser> implements IWishUserService {
    @Override
    public WishUser existsById(Long id) {
        WishUser wishBuyer = getById(id);
        if (ObjectUtil.isNull(wishBuyer)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return wishBuyer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(WishUserFormDTO wishBuyerFormDTO) {
        existsById(wishBuyerFormDTO.getId());
        return updateById(BeanUtil.copyProperties(wishBuyerFormDTO, WishUser.class));
    }

    @Override
    public WishUserVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), WishUserVO.class);
    }

    @Override
    public PageVO<WishUserVO> listByPage(WishUserQueryDTO wishBuyerQueryDTO) {
        Page<WishUser> wishBuyerPage = new Page<>(wishBuyerQueryDTO.getPageNo(), wishBuyerQueryDTO.getPageSize());
        IPage<WishUserVO> wishBuyerVOIPage = page(wishBuyerPage, Wrappers.<WishUser>query().lambda()
                //todo query condition
                .eq(ObjectUtil.isNotEmpty(wishBuyerQueryDTO.getState()), WishUser::getState, wishBuyerQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(wishBuyerQueryDTO.getBeginTime(), wishBuyerQueryDTO.getEndTime()), WishUser::getCreateTime, wishBuyerQueryDTO.getBeginTime(), wishBuyerQueryDTO.getEndTime())
                .orderByDesc(WishUser::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, WishUserVO.class));
        return new PageVO<>(wishBuyerVOIPage.getTotal(), wishBuyerVOIPage.getRecords());
    }


    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<WishUser>update().lambda().eq(WishUser::getId, stateActionDTO.getId())
                .eq(WishUser::getState, !stateActionDTO.getState())
                .set(WishUser::getState, stateActionDTO.getState()));
    }
}
