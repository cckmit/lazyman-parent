package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.common.auth.dto.AppLoginDTO;
import org.lazyman.boot.common.auth.service.IAuthService;
import org.lazyman.boot.common.auth.vo.LoginVO;
import org.lazyman.boot.user.service.IAppUserService;
import org.lazyman.boot.user.vo.AppUserVO;
import org.lazyman.common.util.ThreadLocalUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 系统认证 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-22
 */
@Api(tags = "APP认证接口")
@RestController
@RequestMapping("/api/app")
public class AppAuthController {
    @Resource
    private IAuthService iAuthService;
    @Resource
    private IAppUserService iAppUserService;

    @ApiOperation(value = "发送短信验证码")
    @PostMapping("/auth/sms")
    public ResultVO<Boolean> sendSmsVerifyCode(@ApiParam(value = "手机号", required = true) @RequestParam("mobile") String mobile) {
        iAuthService.sendSmsVerifyCode(mobile);
        return ResultVO.ok(true);
    }

    @ApiOperation(value = "微信授权登录认证")
    @PostMapping("/auth/app/wechat")
    public ResultVO<LoginVO> wechatAuth(@ApiParam(value = "微信授权code", required = true) @RequestParam("code") String code) {
        AppLoginDTO appLoginDTO = new AppLoginDTO();
        appLoginDTO.setOpenId(code);
        return ResultVO.ok(iAuthService.loginApp(appLoginDTO));
    }

    @ApiOperation(value = "APP手机登录认证")
    @PostMapping("/auth/login")
    public ResultVO<LoginVO> loginApp(@Valid @RequestBody AppLoginDTO appLoginDTO) {
        iAuthService.checkSmsVerifyCode(appLoginDTO.getMobile(), appLoginDTO.getVerifyCode());
        return ResultVO.ok(iAuthService.loginApp(appLoginDTO));
    }

    @ApiOperation(value = "注销登录")
    @DeleteMapping("/logout")
    public ResultVO logout() {
        iAuthService.logout(ThreadLocalUtils.getCurrentUserId());
        return ResultVO.ok();
    }

    @ApiOperation(value = "APP绑定手机号码")
    @PostMapping("/bindMobile")
    public ResultVO<Boolean> bindAppMobile(@Valid @RequestBody AppLoginDTO appLoginDTO) {
        iAuthService.checkSmsVerifyCode(appLoginDTO.getMobile(), appLoginDTO.getVerifyCode());
        return ResultVO.ok(iAuthService.bindAppMobile(appLoginDTO));
    }

    @ApiOperation(value = "获取App当前登录用户信息")
    @GetMapping("/userInfo")
    public ResultVO<AppUserVO> getAppUserInfo() {
        return ResultVO.ok(iAppUserService.getDetail(ThreadLocalUtils.getCurrentUserId()));
    }
}
