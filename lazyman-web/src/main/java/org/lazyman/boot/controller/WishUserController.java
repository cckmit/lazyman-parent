package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.wish.dto.WishUserFormDTO;
import org.lazyman.boot.wish.dto.WishUserQueryDTO;
import org.lazyman.boot.wish.service.IWishUserService;
import org.lazyman.boot.wish.vo.WishUserVO;
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
 * wish用户管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
@Api(tags = "wish用户管理接口")
@RestController
@RequestMapping("/api/wishbuyers")
public class WishUserController extends BaseController {
    @Resource
    private IWishUserService iWishUserService;

    @ApiOperation(value = "编辑wish用户")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editWishUser(@ApiParam(value = "wish用户ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody WishUserFormDTO wishUserFormDTO) {
        wishUserFormDTO.setId(id);
        return ResultVO.ok(iWishUserService.edit(wishUserFormDTO));
    }

    @ApiOperation(value = "根据ID查询wish用户详情")
    @GetMapping(value = "/{id}")
    public ResultVO<WishUserVO> getWishUserDetail(@ApiParam(value = "wish用户ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iWishUserService.getDetail(id));
    }

    @ApiOperation(value = "分页查询wish用户")
    @GetMapping
    public ResultVO<PageVO<WishUserVO>> listByPage(WishUserQueryDTO wishUserQueryDTO) {
        return ResultVO.ok(iWishUserService.listByPage(wishUserQueryDTO));
    }

    @ApiOperation(value = "启用/禁用wish用户")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "wish用户ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iWishUserService.updateState(stateActionDTO));
    }
}
