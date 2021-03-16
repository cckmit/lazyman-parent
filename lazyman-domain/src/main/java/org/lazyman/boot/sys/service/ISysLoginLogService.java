package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.sys.dto.SysLoginLogQueryDTO;
import org.lazyman.boot.sys.entity.SysLoginLog;
import org.lazyman.boot.sys.vo.SysLoginLogVO;

/**
 * <p>
 * 系统登录日志 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
public interface ISysLoginLogService extends BaseService<SysLoginLog> {
    /**
     * 新增
     *
     * @param username
     * @param state
     * @param remark
     * @return
     */
    Long save(String username, Boolean state, String remark);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    Boolean delete(Long[] ids);

    /**
     * 逻辑删除
     *
     * @return
     */
    Boolean deleteAll();

    /**
     * 分页查询
     *
     * @param sysLoginLogQueryDTO
     * @return
     */
    PageVO<SysLoginLogVO> listByPage(SysLoginLogQueryDTO sysLoginLogQueryDTO);
}
