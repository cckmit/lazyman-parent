package org.lazyman.boot.user.service;

import org.lazyman.boot.user.dto.AppUserFormDTO;
import org.lazyman.boot.user.dto.AppUserQueryDTO;
import org.lazyman.boot.user.entity.AppUser;
import org.lazyman.boot.user.vo.AppUserVO;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;

/**
 * <p>
 * APP用户 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
public interface IAppUserService extends BaseService<AppUser> {
    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    AppUser existsById(Long id);

    /**
     * 编辑
     *
     * @param appBuyerFormDTO
     * @return
     */
    Boolean edit(AppUserFormDTO appBuyerFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    AppUserVO getDetail(Long id);

    /**
     * 分页查询
     *
     * @param appBuyerQueryDTO
     * @return
     */
    PageVO<AppUserVO> listByPage(AppUserQueryDTO appBuyerQueryDTO);


    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
