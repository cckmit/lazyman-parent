package org.lazyman.boot.sms.service;

import org.lazyman.boot.sms.bo.SmsMessageBO;
import org.lazyman.boot.sms.dto.SmsTaskFormDTO;
import org.lazyman.boot.sms.dto.SmsTaskQueryDTO;
import org.lazyman.boot.sms.entity.SmsTask;
import org.lazyman.boot.sms.vo.SmsTaskVO;
import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.base.vo.PageVO;

/**
 * <p>
 * 短信任务表 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
public interface ISmsTaskService extends BaseService<SmsTask> {
    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SmsTask existsById(Long id);

    /**
     * 创建并发送
     *
     * @param smsTaskFormDTO
     * @return
     */
    Long createAndSend(SmsTaskFormDTO smsTaskFormDTO);

    /**
     * 处理短信发送任务
     *
     * @param smsMessageBO
     */
    void handleSmsTask(SmsMessageBO smsMessageBO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SmsTaskVO getDetail(Long id);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    Boolean delete(Long[] ids);

    /**
     * 分页查询
     *
     * @param smsTaskQueryDTO
     * @return
     */
    PageVO<SmsTaskVO> listByPage(SmsTaskQueryDTO smsTaskQueryDTO);
}
