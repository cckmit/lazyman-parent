package org.lazyman.boot.auth.service;

import org.lazyman.boot.auth.dto.AppLoginDTO;
import org.lazyman.boot.auth.dto.WebLoginDTO;
import org.lazyman.boot.auth.vo.LoginVO;
import org.lazyman.boot.permission.Logical;

/**
 * <p>
 * 系统认证 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-22
 */
public interface IAuthService {
    /**
     * 认证前置处理
     *
     * @return
     */
    boolean preAuth(String accessToken);

    /**
     * 认证后置处理
     */
    void afterAuth();

    /**
     * 检查角色
     *
     * @param roles
     * @param logical
     */
    boolean checkRole(String[] roles, Logical logical);

    /**
     * 检查权限
     *
     * @param permissions
     * @param logical
     */
    boolean checkPermission(String[] permissions, Logical logical);

    /**
     * 管理端登录认证
     *
     * @param webLoginDTO
     * @return
     */
    LoginVO loginWeb(WebLoginDTO webLoginDTO);

    /**
     * 注销登录
     *
     * @param userId
     */
    void logout(Long userId);

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    void sendSmsVerifyCode(String mobile);

    /**
     * 校验验证码
     *
     * @param mobile
     * @param verifyCode
     */
    void checkSmsVerifyCode(String mobile, String verifyCode);

    /**
     * App登录
     *
     * @param appLoginDTO
     * @return
     */
    LoginVO loginApp(AppLoginDTO appLoginDTO);

    /**
     * App绑定手机号码
     *
     * @param appLoginDTO
     * @return
     */
    Boolean bindAppMobile(AppLoginDTO appLoginDTO);
}
