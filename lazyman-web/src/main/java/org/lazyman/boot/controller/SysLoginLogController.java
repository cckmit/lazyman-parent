package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.sys.dto.SysLoginLogQueryDTO;
import org.lazyman.boot.sys.service.ISysLoginLogService;
import org.lazyman.boot.sys.vo.SysLoginLogVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 系统登录日志管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Api(tags = "系统登录日志管理接口")
@RestController
@RequestMapping("/api/sysloginlogs")
public class SysLoginLogController extends BaseController {
    @Resource
    private ISysLoginLogService iSysLoginLogService;

    @ApiOperation(value = "删除系统登录日志")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysLoginLog(@ApiParam(value = "系统登录日志ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysLoginLogService.delete(ids));
    }

    @ApiOperation(value = "清空系统登录日志")
    @DeleteMapping
    public ResultVO deleteAllSysLoginLog() {
        return ResultVO.ok(iSysLoginLogService.deleteAll());
    }

    @ApiOperation(value = "分页查询系统登录日志")
    @GetMapping
    public ResultVO<PageVO<SysLoginLogVO>> listByPage(SysLoginLogQueryDTO sysLoginLogQueryDTO) {
        return ResultVO.ok(iSysLoginLogService.listByPage(sysLoginLogQueryDTO));
    }
}
