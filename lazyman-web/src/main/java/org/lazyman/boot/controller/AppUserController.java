package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.user.dto.AppUserFormDTO;
import org.lazyman.boot.user.dto.AppUserQueryDTO;
import org.lazyman.boot.user.service.IAppUserService;
import org.lazyman.boot.user.vo.AppUserVO;
import org.lazyman.core.base.controller.BaseController;
import org.lazyman.core.base.dto.StateActionDTO;
import org.lazyman.core.base.vo.PageVO;
import org.lazyman.core.base.vo.ResultVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * APP用户管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
@Api(tags = "APP用户管理接口")
@RestController
@RequestMapping("/api/wishbuyers")
public class AppUserController extends BaseController {
    @Resource
    private IAppUserService iAppUserService;

    @ApiOperation(value = "编辑APP用户")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editWishUser(@ApiParam(value = "APP用户ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody AppUserFormDTO appUserFormDTO) {
        appUserFormDTO.setId(id);
        return ResultVO.ok(iAppUserService.edit(appUserFormDTO));
    }

    @ApiOperation(value = "根据ID查询APP用户详情")
    @GetMapping(value = "/{id}")
    public ResultVO<AppUserVO> getWishUserDetail(@ApiParam(value = "APP用户ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iAppUserService.getDetail(id));
    }

    @ApiOperation(value = "分页查询APP用户")
    @GetMapping
    public ResultVO<PageVO<AppUserVO>> listByPage(AppUserQueryDTO appUserQueryDTO) {
        return ResultVO.ok(iAppUserService.listByPage(appUserQueryDTO));
    }

    @ApiOperation(value = "启用/禁用APP用户")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "APP用户ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iAppUserService.updateState(stateActionDTO));
    }
}
