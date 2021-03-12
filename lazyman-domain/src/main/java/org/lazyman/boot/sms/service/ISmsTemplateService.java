package org.lazyman.boot.sms.service;

import org.lazyman.boot.sms.dto.SmsTemplateFormDTO;
import org.lazyman.boot.sms.dto.SmsTemplateQueryDTO;
import org.lazyman.boot.sms.entity.SmsTemplate;
import org.lazyman.boot.sms.vo.SmsTemplateVO;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.service.BaseService;
import org.lazyman.core.base.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 短信模板 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
public interface ISmsTemplateService extends BaseService<SmsTemplate> {
    /**
     * 通过表单数据检查是否重复创建
     *
     * @param smsTemplateFormDTO
     * @return
     */
    Boolean exists(SmsTemplateFormDTO smsTemplateFormDTO);

    /**
     * 通过ID检查是否存在
     *
     * @param id
     * @return
     */
    SmsTemplate existsById(Long id);

    /**
     * 新增
     *
     * @param smsTemplateFormDTO
     * @return
     */
    Long save(SmsTemplateFormDTO smsTemplateFormDTO);

    /**
     * 编辑
     *
     * @param smsTemplateFormDTO
     * @return
     */
    Boolean edit(SmsTemplateFormDTO smsTemplateFormDTO);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    SmsTemplateVO getDetail(Long id);

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
     * @param smsTemplateQueryDTO
     * @return
     */
    PageVO<SmsTemplateVO> listByPage(SmsTemplateQueryDTO smsTemplateQueryDTO);

    /**
     * 加载下拉选项，支持关键字搜索
     *
     * @param keyword
     * @return
     */
    List<SmsTemplateVO> listSelectOptions(String keyword);

    /**
     * 状态操作,一般用于启用禁用操作
     *
     * @param stateActionDTO
     * @return
     */
    Boolean updateState(StateActionDTO stateActionDTO);
}
