package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sys.dto.SysDeptFormDTO;
import org.lazyman.boot.sys.dto.SysDeptQueryDTO;
import org.lazyman.boot.sys.service.ISysDeptService;
import org.lazyman.boot.sys.vo.SysDeptTreeVO;
import org.lazyman.boot.sys.vo.SysDeptVO;
import org.lazyman.boot.sys.vo.VueElTreeVO;
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
 * 系统部门管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
@Api(tags = "系统部门管理接口")
@RestController
@RequestMapping("/api/sysdepts")
public class SysDeptController extends BaseController {
    @Resource
    private ISysDeptService iSysDeptService;

    @ApiOperation(value = "新增系统部门")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysDept(@Valid @RequestBody SysDeptFormDTO sysDeptFormDTO) {
        return ResultVO.ok(iSysDeptService.save(sysDeptFormDTO));
    }

    @ApiOperation(value = "编辑系统部门")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysDept(@ApiParam(value = "系统部门ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysDeptFormDTO sysDeptFormDTO) {
        sysDeptFormDTO.setId(id);
        return ResultVO.ok(iSysDeptService.edit(sysDeptFormDTO));
    }

    @ApiOperation(value = "删除系统部门")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysDept(@ApiParam(value = "系统部门ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysDeptService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统部门详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysDeptVO> getSysDeptDetail(@ApiParam(value = "系统部门ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysDeptService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统部门")
    @GetMapping
    public ResultVO<PageVO<SysDeptVO>> listByPage(SysDeptQueryDTO sysDeptQueryDTO) {
        return ResultVO.ok(iSysDeptService.listByPage(sysDeptQueryDTO));
    }

    @ApiOperation(value = "启用/禁用系统部门")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统部门ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysDeptService.updateState(stateActionDTO));
    }

    @ApiOperation(value = "查询系统部门树列表")
    @GetMapping(value = "/treeList")
    public ResultVO<List<SysDeptTreeVO>> listByTree(SysDeptQueryDTO sysDeptQueryDTO) {
        return ResultVO.ok(iSysDeptService.listByTree(sysDeptQueryDTO));
    }

    @ApiOperation(value = "根据系统部门父ID查询子节点列表")
    @GetMapping(value = "/children/{parentId}")
    public ResultVO<List<SysDeptVO>> listChildrensByParentId(@ApiParam(value = "系统部门父ID", required = true) @PathVariable("parentId") Long parentId) {
        return ResultVO.ok(iSysDeptService.listChildrensByParentId(parentId));
    }

    @ApiOperation(value = "查询vue el部门树列表")
    @GetMapping(value = "/treeDeptList")
    public ResultVO<List<VueElTreeVO>> listVueElDeptByTree() {
        return ResultVO.ok(iSysDeptService.listVueElDeptByTree());
    }
}
