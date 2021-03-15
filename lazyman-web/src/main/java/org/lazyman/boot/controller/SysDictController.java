package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sys.dto.SysDictFormDTO;
import org.lazyman.boot.sys.dto.SysDictQueryDTO;
import org.lazyman.boot.sys.service.ISysDictService;
import org.lazyman.boot.sys.vo.SysDictDataVO;
import org.lazyman.boot.sys.vo.SysDictVO;
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
 * 系统字典管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
@Api(tags = "系统字典管理接口")
@RestController
@RequestMapping("/api/sysdicts")
public class SysDictController extends BaseController {
    @Resource
    private ISysDictService iSysDictService;

    @ApiOperation(value = "新增系统字典")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysDict(@Valid @RequestBody SysDictFormDTO sysDictFormDTO) {
        return ResultVO.ok(iSysDictService.save(sysDictFormDTO));
    }

    @ApiOperation(value = "编辑系统字典")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysDict(@ApiParam(value = "系统字典ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysDictFormDTO sysDictFormDTO) {
        sysDictFormDTO.setId(id);
        return ResultVO.ok(iSysDictService.edit(sysDictFormDTO));
    }

    @ApiOperation(value = "删除系统字典")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysDict(@ApiParam(value = "系统字典ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysDictService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统字典详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysDictVO> getSysDictDetail(@ApiParam(value = "系统字典ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysDictService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统字典")
    @GetMapping
    public ResultVO<PageVO<SysDictVO>> listByPage(SysDictQueryDTO sysDictQueryDTO) {
        return ResultVO.ok(iSysDictService.listByPage(sysDictQueryDTO));
    }

    @ApiOperation(value = "查询系统字典下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SysDictVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSysDictService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用系统字典")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统字典ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysDictService.updateState(stateActionDTO));
    }

    @ApiOperation(value = "查询系统字典数据列表")
    @GetMapping(value = "/datas/{dictType}")
    public ResultVO<List<SysDictDataVO>> listDictDatas(@ApiParam(value = "字典类型") @PathVariable("dictType") String dictType) {
        return ResultVO.ok(iSysDictService.listDictDatas(dictType));
    }
}
