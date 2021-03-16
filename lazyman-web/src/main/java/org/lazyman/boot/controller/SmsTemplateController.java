package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.sms.dto.SmsTemplateFormDTO;
import org.lazyman.boot.sms.dto.SmsTemplateQueryDTO;
import org.lazyman.boot.sms.service.ISmsTemplateService;
import org.lazyman.boot.sms.vo.SmsTemplateVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 短信模板管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Api(tags = "短信模板管理接口")
@RestController
@RequestMapping("/api/smstemplates")
public class SmsTemplateController extends BaseController {
    @Resource
    private ISmsTemplateService iSmsTemplateService;

    @ApiOperation(value = "新增短信模板")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSmsTemplate(@Valid @RequestBody SmsTemplateFormDTO smsTemplateFormDTO) {
        return ResultVO.ok(iSmsTemplateService.save(smsTemplateFormDTO));
    }

    @ApiOperation(value = "编辑短信模板")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSmsTemplate(@ApiParam(value = "短信模板ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SmsTemplateFormDTO smsTemplateFormDTO) {
        smsTemplateFormDTO.setId(id);
        return ResultVO.ok(iSmsTemplateService.edit(smsTemplateFormDTO));
    }

    @ApiOperation(value = "删除短信模板")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSmsTemplate(@ApiParam(value = "短信模板ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSmsTemplateService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询短信模板详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SmsTemplateVO> getSmsTemplateDetail(@ApiParam(value = "短信模板ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSmsTemplateService.getDetail(id));
    }

    @ApiOperation(value = "分页查询短信模板")
    @GetMapping
    public ResultVO<PageVO<SmsTemplateVO>> listByPage(SmsTemplateQueryDTO smsTemplateQueryDTO) {
        return ResultVO.ok(iSmsTemplateService.listByPage(smsTemplateQueryDTO));
    }

    @ApiOperation(value = "查询短信模板下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SmsTemplateVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSmsTemplateService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用短信模板")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "短信模板ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSmsTemplateService.updateState(stateActionDTO));
    }
}
