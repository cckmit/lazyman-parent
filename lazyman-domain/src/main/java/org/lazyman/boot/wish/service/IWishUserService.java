package org.lazyman.boot.wish.service;

import org.lazyman.boot.wish.dto.WishUserFormDTO;
import org.lazyman.boot.wish.dto.WishUserQueryDTO;
import org.lazyman.boot.wish.entity.WishUser;
import org.lazyman.boot.wish.vo.WishUserVO;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.service.BaseService;
import org.lazyman.core.base.vo.PageVO;

/**
 * <p>
 * wish买家 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
public interface IWishUserService extends BaseService<WishUser> {
    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    WishUser existsById(Long id);

    /**
     * 编辑
     *
     * @param wishBuyerFormDTO
     * @return
     */
    Boolean edit(WishUserFormDTO wishBuyerFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    WishUserVO getDetail(Long id);

    /**
     * 分页查询
     *
     * @param wishBuyerQueryDTO
     * @return
     */
    PageVO<WishUserVO> listByPage(WishUserQueryDTO wishBuyerQueryDTO);


    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
