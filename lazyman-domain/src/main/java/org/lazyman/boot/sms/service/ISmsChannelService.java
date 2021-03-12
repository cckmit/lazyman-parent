package org.lazyman.boot.sms.service;

import org.lazyman.boot.sms.entity.SmsChannel;
import org.lazyman.boot.sms.dto.SmsChannelFormDTO;
import org.lazyman.boot.sms.vo.SmsChannelVO;
import org.lazyman.boot.sms.dto.SmsChannelQueryDTO;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.vo.PageVO;
import java.util.List;
import org.lazyman.core.base.service.BaseService;

/**
 * <p>
 * 短信通道 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
public interface ISmsChannelService extends BaseService<SmsChannel> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param smsChannelFormDTO
     * @return
     */
    Boolean exists(SmsChannelFormDTO smsChannelFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SmsChannel existsById(Long id);

    /**
     * 新增
     *
     * @param smsChannelFormDTO
     * @return
     */
    Long save(SmsChannelFormDTO smsChannelFormDTO);

    /**
     * 编辑
     *
     * @param smsChannelFormDTO
     * @return
     */
    Boolean edit(SmsChannelFormDTO smsChannelFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
     SmsChannelVO getDetail(Long id);

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
    * @param smsChannelQueryDTO
    * @return
    */
    PageVO<SmsChannelVO> listByPage(SmsChannelQueryDTO smsChannelQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SmsChannelVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
