package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.sys.dto.SysDictDataFormDTO;
import org.lazyman.boot.sys.dto.SysDictDataQueryDTO;
import org.lazyman.boot.sys.service.ISysDictDataService;
import org.lazyman.boot.sys.vo.SysDictDataVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 系统字典数据管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
@Api(tags = "系统字典数据管理接口")
@RestController
@RequestMapping("/api/sysdictdatas")
public class SysDictDataController extends BaseController {
    @Resource
    private ISysDictDataService iSysDictDataService;

    @ApiOperation(value = "新增系统字典数据")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysDictData(@Valid @RequestBody SysDictDataFormDTO sysDictDataFormDTO) {
        return ResultVO.ok(iSysDictDataService.save(sysDictDataFormDTO));
    }

    @ApiOperation(value = "编辑系统字典数据")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysDictData(@ApiParam(value = "系统字典数据ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysDictDataFormDTO sysDictDataFormDTO) {
        sysDictDataFormDTO.setId(id);
        return ResultVO.ok(iSysDictDataService.edit(sysDictDataFormDTO));
    }

    @ApiOperation(value = "删除系统字典数据")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysDictData(@ApiParam(value = "系统字典数据ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysDictDataService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统字典数据详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysDictDataVO> getSysDictDataDetail(@ApiParam(value = "系统字典数据ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysDictDataService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统字典数据")
    @GetMapping
    public ResultVO<PageVO<SysDictDataVO>> listByPage(SysDictDataQueryDTO sysDictDataQueryDTO) {
        return ResultVO.ok(iSysDictDataService.listByPage(sysDictDataQueryDTO));
    }

    @ApiOperation(value = "启用/禁用系统字典数据")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统字典数据ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysDictDataService.updateState(stateActionDTO));
    }
}
