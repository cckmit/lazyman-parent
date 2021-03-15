package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sms.dto.SmsTaskFormDTO;
import org.lazyman.boot.sms.dto.SmsTaskQueryDTO;
import org.lazyman.boot.sms.service.ISmsTaskService;
import org.lazyman.boot.sms.vo.SmsTaskVO;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 短信任务表管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Api(tags = "短信任务表管理接口")
@RestController
@RequestMapping("/api/smstasks")
public class SmsTaskController extends BaseController {
    @Resource
    private ISmsTaskService iSmsTaskService;

    @ApiOperation(value = "创建并发送短信任务")
    @PostMapping
    public ResultVO createAndSendSmsTask(@Valid @RequestBody SmsTaskFormDTO smsTaskFormDTO) {
        return ResultVO.ok(iSmsTaskService.createAndSend(smsTaskFormDTO));
    }

    @ApiOperation(value = "删除短信任务表")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSmsTask(@ApiParam(value = "短信任务表ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSmsTaskService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询短信任务表详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SmsTaskVO> getSmsTaskDetail(@ApiParam(value = "短信任务表ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSmsTaskService.getDetail(id));
    }

    @ApiOperation(value = "分页查询短信任务表")
    @GetMapping
    public ResultVO<PageVO<SmsTaskVO>> listByPage(SmsTaskQueryDTO smsTaskQueryDTO) {
        return ResultVO.ok(iSmsTaskService.listByPage(smsTaskQueryDTO));
    }
}
