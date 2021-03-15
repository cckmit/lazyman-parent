package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lazyman.boot.auth.dto.WebLoginDTO;
import org.lazyman.boot.auth.service.IAuthService;
import org.lazyman.boot.auth.service.IKaptchaService;
import org.lazyman.boot.auth.vo.KaptchaVO;
import org.lazyman.boot.auth.vo.LoginUserInfoVO;
import org.lazyman.boot.auth.vo.LoginVO;
import org.lazyman.boot.sys.service.ISysUserService;
import org.lazyman.boot.sys.vo.VueRouterTreeVO;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.core.base.vo.ResultVO;
import org.lazyman.starter.redisson.annotation.RequireRateLimit;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统认证 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-22
 */
@Api(tags = "系统认证接口")
@RestController
@RequestMapping("/api")
public class SysAuthController {
    @Resource
    private IKaptchaService iKaptchaService;
    @Resource
    private IAuthService iAuthService;
    @Resource
    private ISysUserService iSysUserService;

    @ApiOperation(value = "生成图形验证码")
    @PostMapping("/auth/kaptcha")
    @RequireRateLimit(count = 1)
    public ResultVO<KaptchaVO> generateKaptcha() {
        return ResultVO.ok(iKaptchaService.generateKaptcha());
    }

    @ApiOperation(value = "Web登录认证")
    @PostMapping("/auth/login")
    public ResultVO<LoginVO> login(@Valid @RequestBody WebLoginDTO webLoginDTO) {
        iKaptchaService.verifyKaptcha(webLoginDTO.getUsername(), webLoginDTO.getUuid(), webLoginDTO.getCode());
        return ResultVO.ok(iAuthService.loginWeb(webLoginDTO));
    }

    @ApiOperation(value = "注销登录")
    @DeleteMapping("/logout")
    public ResultVO logout() {
        iAuthService.logout(ThreadLocalUtils.getCurrentUserId());
        return ResultVO.ok();
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/userInfo")
    public ResultVO<LoginUserInfoVO> getUserInfo() {
        return ResultVO.ok(iSysUserService.getUserInfo(ThreadLocalUtils.getCurrentUserId()));
    }

    @ApiOperation(value = "查询当前登录用户路由树")
    @GetMapping("/routerInfo")
    public ResultVO<List<VueRouterTreeVO>> listUserRouterByTree() {
        return ResultVO.ok(iSysUserService.listUserRouterByTree(ThreadLocalUtils.getCurrentUserId()));
    }
}
