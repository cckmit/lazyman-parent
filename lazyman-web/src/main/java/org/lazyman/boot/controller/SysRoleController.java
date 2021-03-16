package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.sys.dto.SysRoleDataScopeFormDTO;
import org.lazyman.boot.sys.dto.SysRoleFormDTO;
import org.lazyman.boot.sys.dto.SysRoleQueryDTO;
import org.lazyman.boot.sys.service.ISysRoleService;
import org.lazyman.boot.sys.vo.SysRoleVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统角色管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Api(tags = "系统角色管理接口")
@RestController
@RequestMapping("/api/sysroles")
public class SysRoleController extends BaseController {
    @Resource
    private ISysRoleService iSysRoleService;

    @ApiOperation(value = "新增系统角色")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysRole(@Valid @RequestBody SysRoleFormDTO sysRoleFormDTO) {
        return ResultVO.ok(iSysRoleService.save(sysRoleFormDTO));
    }

    @ApiOperation(value = "编辑系统角色")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysRole(@ApiParam(value = "系统角色ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysRoleFormDTO sysRoleFormDTO) {
        sysRoleFormDTO.setId(id);
        return ResultVO.ok(iSysRoleService.edit(sysRoleFormDTO));
    }

    @ApiOperation(value = "删除系统角色")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysRole(@ApiParam(value = "系统角色ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysRoleService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统角色详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysRoleVO> getSysRoleDetail(@ApiParam(value = "系统角色ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysRoleService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统角色")
    @GetMapping
    public ResultVO<PageVO<SysRoleVO>> listByPage(SysRoleQueryDTO sysRoleQueryDTO) {
        return ResultVO.ok(iSysRoleService.listByPage(sysRoleQueryDTO));
    }

    @ApiOperation(value = "查询系统角色下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SysRoleVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSysRoleService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用系统角色")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统角色ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysRoleService.updateState(stateActionDTO));
    }

    @ApiOperation(value = "查询系统角色的菜单权限集合")
    @GetMapping(value = "/menus/{roleId}")
    public ResultVO<List<Long>> listRoleMenuPermissions(@ApiParam(value = "系统角色ID", required = true) @PathVariable(value = "roleId") Long roleId) {
        return ResultVO.ok(iSysRoleService.listRoleMenuPermissions(roleId));
    }

    @ApiOperation(value = "设置数据权限")
    @PutMapping(value = "/datascope/{id}")
    @Idempotency
    public ResultVO setDataScope(@ApiParam(value = "系统角色ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysRoleDataScopeFormDTO sysRoleDataScopeFormDTO) {
        sysRoleDataScopeFormDTO.setId(id);
        return ResultVO.ok(iSysRoleService.setDataScope(sysRoleDataScopeFormDTO));
    }

    @ApiOperation(value = "查询系统角色的数据权限集合")
    @GetMapping(value = "/datascope/{roleId}")
    public ResultVO<List<Long>> listRoleDataScope(@ApiParam(value = "系统角色ID", required = true) @PathVariable(value = "roleId") Long roleId) {
        return ResultVO.ok(iSysRoleService.listRoleDataScope(roleId));
    }
}
