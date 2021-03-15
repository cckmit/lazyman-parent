package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sys.dto.SysConfigFormDTO;
import org.lazyman.boot.sys.dto.SysConfigQueryDTO;
import org.lazyman.boot.sys.service.ISysConfigService;
import org.lazyman.boot.sys.vo.SysConfigVO;
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
 * 系统配置管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Api(tags = "系统配置管理接口")
@RestController
@RequestMapping("/api/sysconfigs")
public class SysConfigController extends BaseController {
    @Resource
    private ISysConfigService iSysConfigService;

    @ApiOperation(value = "新增系统配置")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysConfig(@Valid @RequestBody SysConfigFormDTO sysConfigFormDTO) {
        return ResultVO.ok(iSysConfigService.save(sysConfigFormDTO));
    }

    @ApiOperation(value = "编辑系统配置")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysConfig(@ApiParam(value = "系统配置ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysConfigFormDTO sysConfigFormDTO) {
        sysConfigFormDTO.setId(id);
        return ResultVO.ok(iSysConfigService.edit(sysConfigFormDTO));
    }

    @ApiOperation(value = "删除系统配置")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysConfig(@ApiParam(value = "系统配置ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysConfigService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统配置详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysConfigVO> getSysConfigDetail(@ApiParam(value = "系统配置ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysConfigService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统配置")
    @GetMapping
    public ResultVO<PageVO<SysConfigVO>> listByPage(SysConfigQueryDTO sysConfigQueryDTO) {
        return ResultVO.ok(iSysConfigService.listByPage(sysConfigQueryDTO));
    }

    @ApiOperation(value = "查询系统配置下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SysConfigVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSysConfigService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用系统配置")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统配置ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysConfigService.updateState(stateActionDTO));
    }
}
