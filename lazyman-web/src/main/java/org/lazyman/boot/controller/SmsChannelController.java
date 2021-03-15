package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sms.dto.SmsChannelFormDTO;
import org.lazyman.boot.sms.dto.SmsChannelQueryDTO;
import org.lazyman.boot.sms.service.ISmsChannelService;
import org.lazyman.boot.sms.vo.SmsChannelVO;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 短信通道管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Api(tags = "短信通道管理接口")
@RestController
@RequestMapping("/api/smschannels")
public class SmsChannelController extends BaseController {
    @Resource
    private ISmsChannelService iSmsChannelService;

    @ApiOperation(value = "新增短信通道")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSmsChannel(@Valid @RequestBody SmsChannelFormDTO smsChannelFormDTO) {
        return ResultVO.ok(iSmsChannelService.save(smsChannelFormDTO));
    }

    @ApiOperation(value = "编辑短信通道")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSmsChannel(@ApiParam(value = "短信通道ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SmsChannelFormDTO smsChannelFormDTO) {
        smsChannelFormDTO.setId(id);
        return ResultVO.ok(iSmsChannelService.edit(smsChannelFormDTO));
    }

    @ApiOperation(value = "删除短信通道")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSmsChannel(@ApiParam(value = "短信通道ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSmsChannelService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询短信通道详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SmsChannelVO> getSmsChannelDetail(@ApiParam(value = "短信通道ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSmsChannelService.getDetail(id));
    }

    @ApiOperation(value = "分页查询短信通道")
    @GetMapping
    public ResultVO<PageVO<SmsChannelVO>> listByPage(SmsChannelQueryDTO smsChannelQueryDTO) {
        return ResultVO.ok(iSmsChannelService.listByPage(smsChannelQueryDTO));
    }

    @ApiOperation(value = "查询短信通道下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SmsChannelVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSmsChannelService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用短信通道")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "短信通道ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSmsChannelService.updateState(stateActionDTO));
    }
}
