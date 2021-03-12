package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sys.dto.SysNoticeFormDTO;
import org.lazyman.boot.sys.dto.SysNoticeQueryDTO;
import org.lazyman.boot.sys.service.ISysNoticeService;
import org.lazyman.boot.sys.vo.SysNoticeVO;
import org.lazyman.core.base.controller.BaseController;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.vo.PageVO;
import org.lazyman.core.base.vo.ResultVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统通知公告管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Api(tags = "系统通知公告管理接口")
@RestController
@RequestMapping("/api/sysnotices")
public class SysNoticeController extends BaseController {
    @Resource
    private ISysNoticeService iSysNoticeService;

    @ApiOperation(value = "新增系统通知公告")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysNotice(@Valid @RequestBody SysNoticeFormDTO sysNoticeFormDTO) {
        return ResultVO.ok(iSysNoticeService.save(sysNoticeFormDTO));
    }

    @ApiOperation(value = "编辑系统通知公告")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysNotice(@ApiParam(value = "系统通知公告ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysNoticeFormDTO sysNoticeFormDTO) {
        sysNoticeFormDTO.setId(id);
        return ResultVO.ok(iSysNoticeService.edit(sysNoticeFormDTO));
    }

    @ApiOperation(value = "删除系统通知公告")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysNotice(@ApiParam(value = "系统通知公告ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysNoticeService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统通知公告详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysNoticeVO> getSysNoticeDetail(@ApiParam(value = "系统通知公告ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysNoticeService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统通知公告")
    @GetMapping
    public ResultVO<PageVO<SysNoticeVO>> listByPage(SysNoticeQueryDTO sysNoticeQueryDTO) {
        return ResultVO.ok(iSysNoticeService.listByPage(sysNoticeQueryDTO));
    }

    @ApiOperation(value = "查询系统通知公告下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SysNoticeVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSysNoticeService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用系统通知公告")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统通知公告ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysNoticeService.updateState(stateActionDTO));
    }
}
