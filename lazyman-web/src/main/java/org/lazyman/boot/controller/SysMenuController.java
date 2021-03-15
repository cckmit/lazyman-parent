package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sys.dto.SysMenuFormDTO;
import org.lazyman.boot.sys.dto.SysMenuQueryDTO;
import org.lazyman.boot.sys.service.ISysMenuService;
import org.lazyman.boot.sys.vo.SysMenuTreeVO;
import org.lazyman.boot.sys.vo.SysMenuVO;
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
 * 系统菜单管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Api(tags = "系统菜单管理接口")
@RestController
@RequestMapping("/api/sysmenus")
public class SysMenuController extends BaseController {
    @Resource
    private ISysMenuService iSysMenuService;

    @ApiOperation(value = "新增系统菜单")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysMenu(@Valid @RequestBody SysMenuFormDTO sysMenuFormDTO) {
        return ResultVO.ok(iSysMenuService.save(sysMenuFormDTO));
    }

    @ApiOperation(value = "编辑系统菜单")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysMenu(@ApiParam(value = "系统菜单ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysMenuFormDTO sysMenuFormDTO) {
        sysMenuFormDTO.setId(id);
        return ResultVO.ok(iSysMenuService.edit(sysMenuFormDTO));
    }

    @ApiOperation(value = "删除系统菜单")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysMenu(@ApiParam(value = "系统菜单ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysMenuService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统菜单详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysMenuVO> getSysMenuDetail(@ApiParam(value = "系统菜单ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysMenuService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统菜单")
    @GetMapping
    public ResultVO<PageVO<SysMenuVO>> listByPage(SysMenuQueryDTO sysMenuQueryDTO) {
        return ResultVO.ok(iSysMenuService.listByPage(sysMenuQueryDTO));
    }

    @ApiOperation(value = "启用/禁用系统菜单")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统菜单ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysMenuService.updateState(stateActionDTO));
    }

    @ApiOperation(value = "查询系统菜单树列表")
    @GetMapping(value = "/treeList")
    public ResultVO<List<SysMenuTreeVO>> listByTree(SysMenuQueryDTO sysMenuQueryDTO) {
        return ResultVO.ok(iSysMenuService.listByTree(sysMenuQueryDTO));
    }

    @ApiOperation(value = "根据系统菜单父ID查询子节点列表")
    @GetMapping(value = "/children/{parentId}")
    public ResultVO<List<SysMenuVO>> listChildrensByParentId(@ApiParam(value = "系统菜单父ID", required = true) @PathVariable("parentId") Long parentId) {
        return ResultVO.ok(iSysMenuService.listChildrensByParentId(parentId));
    }

    @ApiOperation(value = "查询vue el菜单权限树列表")
    @GetMapping(value = "/treePermissionList")
    public ResultVO<List<VueElTreeVO>> listVueElPermissionByTree() {
        return ResultVO.ok(iSysMenuService.listVueElPermissionByTree());
    }
}
