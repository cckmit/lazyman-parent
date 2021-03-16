package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.sys.dto.SysPostFormDTO;
import org.lazyman.boot.sys.dto.SysPostQueryDTO;
import org.lazyman.boot.sys.service.ISysPostService;
import org.lazyman.boot.sys.vo.SysPostVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统岗位管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
@Api(tags = "系统岗位管理接口")
@RestController
@RequestMapping("/api/sysposts")
public class SysPostController extends BaseController {
    @Resource
    private ISysPostService iSysPostService;

    @ApiOperation(value = "新增系统岗位")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysPost(@Valid @RequestBody SysPostFormDTO sysPostFormDTO) {
        return ResultVO.ok(iSysPostService.save(sysPostFormDTO));
    }

    @ApiOperation(value = "编辑系统岗位")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysPost(@ApiParam(value = "系统岗位ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysPostFormDTO sysPostFormDTO) {
        sysPostFormDTO.setId(id);
        return ResultVO.ok(iSysPostService.edit(sysPostFormDTO));
    }

    @ApiOperation(value = "删除系统岗位")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysPost(@ApiParam(value = "系统岗位ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysPostService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统岗位详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysPostVO> getSysPostDetail(@ApiParam(value = "系统岗位ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysPostService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统岗位")
    @GetMapping
    public ResultVO<PageVO<SysPostVO>> listByPage(SysPostQueryDTO sysPostQueryDTO) {
        return ResultVO.ok(iSysPostService.listByPage(sysPostQueryDTO));
    }

    @ApiOperation(value = "查询系统岗位下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SysPostVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSysPostService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用系统岗位")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统岗位ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysPostService.updateState(stateActionDTO));
    }
}
