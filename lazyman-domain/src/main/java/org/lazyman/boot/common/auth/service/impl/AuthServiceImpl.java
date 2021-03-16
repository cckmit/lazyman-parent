package org.lazyman.boot.common.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.common.auth.dto.AppLoginDTO;
import org.lazyman.boot.common.auth.dto.WebLoginDTO;
import org.lazyman.boot.common.auth.service.IAuthService;
import org.lazyman.boot.common.auth.vo.LoginVO;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.common.constant.LazymanErrCode;
import org.lazyman.boot.sms.dto.SmsTaskFormDTO;
import org.lazyman.boot.sms.service.ISmsTaskService;
import org.lazyman.boot.sys.entity.SysUser;
import org.lazyman.boot.sys.service.ISysLoginLogService;
import org.lazyman.boot.sys.service.ISysUserService;
import org.lazyman.boot.sys.vo.SysMenuVO;
import org.lazyman.boot.sys.vo.SysRoleVO;
import org.lazyman.boot.user.entity.AppUser;
import org.lazyman.boot.user.service.IAppUserService;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.ClientFrom;
import org.lazyman.common.util.JWTUtils;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.common.util.WebUtils;
import org.lazyman.boot.config.JwtTokenProperties;
import org.lazyman.boot.permission.Logical;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.lazyman.starter.redisson.constant.RedisConstant;
import org.redisson.api.RateIntervalUnit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统认证 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-22
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    @Resource
    private RedissonTemplate redissonTemplate;
    @Resource
    private JwtTokenProperties jwtTokenProperties;
    @Resource
    private ISysUserService iSysUserService;
    @Resource
    private ISysLoginLogService iSysLoginLogService;
    @Resource
    private ISmsTaskService iSmsTaskService;
    @Resource
    private IAppUserService iAppUserService;

    @Override
    public boolean preAuth(String accessToken) {
        if (StrUtil.isEmpty(accessToken)) {
            return false;
        }
        Long userId = JWTUtils.parseUserId(accessToken);
        if (ObjectUtil.isEmpty(userId) || !JWTUtils.verify(accessToken, userId, jwtTokenProperties.getSecretKey())) {
            return false;
        }
        String tokenKey = LazymanConstant.RedisKey.AUTH_USER_PREFIX + userId;
        String value = redissonTemplate.get(tokenKey);
        if (ObjectUtil.isEmpty(value)) {
            return false;
        }
        ClientFrom clientFrom = ClientFrom.findByCode(WebUtils.getClientFrom());
        if (ObjectUtil.isEmpty(clientFrom)) {
            clientFrom = ClientFrom.WEB;
        }
        if (ClientFrom.APP.equals(clientFrom)) {
            redissonTemplate.expire(tokenKey, jwtTokenProperties.getAppExpireSeconds(), TimeUnit.SECONDS);
        } else if (ClientFrom.OPENAPI.equals(clientFrom)) {

        } else {
            redissonTemplate.expire(tokenKey, jwtTokenProperties.getWebExpireSeconds(), TimeUnit.SECONDS);
        }
        Long tenantId = JWTUtils.parseTenantId(accessToken);
        ThreadLocalUtils.setCurrentTenantId(tenantId);
        ThreadLocalUtils.setCurrentUserId(userId);
        return true;
    }

    @Override
    public void afterAuth() {
        ThreadLocalUtils.remove();
    }

    @Override
    public boolean checkRole(String[] roles, Logical logical) {
        if (ObjectUtil.isEmpty(roles)) {
            return true;
        }
        List<SysRoleVO> sysRoleVOList = iSysUserService.listUserRoles(ThreadLocalUtils.getCurrentUserId());
        if (CollectionUtil.isEmpty(sysRoleVOList)) {
            return false;
        }
        List<String> roleCodeList = sysRoleVOList.stream().map(SysRoleVO::getRoleCode).collect(Collectors.toList());
        boolean hasPermission = false;
        if (logical == Logical.AND) {
            hasPermission = true;
            for (String role : roles) {
                hasPermission &= roleCodeList.contains(role);
            }
        } else {
            for (String role : roles) {
                if (roleCodeList.contains(role)) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }

    @Override
    public boolean checkPermission(String[] permissions, Logical logical) {
        if (ObjectUtil.isEmpty(permissions)) {
            return true;
        }
        List<SysMenuVO> sysMenuVOList = iSysUserService.listUserPermissions(ThreadLocalUtils.getCurrentUserId());
        if (CollectionUtil.isEmpty(sysMenuVOList)) {
            return false;
        }
        List<String> menuCodeList = sysMenuVOList.stream().map(SysMenuVO::getMenuCode).collect(Collectors.toList());
        boolean hasPermission = false;
        if (logical == Logical.AND) {
            hasPermission = true;
            for (String permission : permissions) {
                hasPermission &= menuCodeList.contains(permission);
            }
        } else {
            for (String permission : permissions) {
                if (menuCodeList.contains(permission)) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }

    @Override
    public LoginVO loginWeb(WebLoginDTO webLoginDTO) {
        SysUser sysUser = iSysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, webLoginDTO.getUsername()));
        if (ObjectUtil.isNull(sysUser)) {
            iSysLoginLogService.save(webLoginDTO.getUsername(), false, LazymanErrCode.USERNAME_PWD_ERROR.getMessage());
            throw new BizException(LazymanErrCode.USERNAME_PWD_ERROR);
        }
        if (!sysUser.getState()) {
            iSysLoginLogService.save(webLoginDTO.getUsername(), false, LazymanErrCode.USER_DISABLED.getMessage());
            throw new BizException(LazymanErrCode.USER_DISABLED);
        }
        if (!BCrypt.checkpw(webLoginDTO.getPassword(), sysUser.getPassword())) {
            iSysLoginLogService.save(webLoginDTO.getUsername(), false, LazymanErrCode.USERNAME_PWD_ERROR.getMessage());
            throw new BizException(LazymanErrCode.USERNAME_PWD_ERROR);
        }
        iSysLoginLogService.save(webLoginDTO.getUsername(), true, "登录成功");
        String token = JWTUtils.sign(sysUser.getId(), jwtTokenProperties.getSecretKey());
        String tokenKey = LazymanConstant.RedisKey.AUTH_USER_PREFIX + sysUser.getId();
        redissonTemplate.set(tokenKey, token, jwtTokenProperties.getWebExpireSeconds(), TimeUnit.SECONDS);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setExpireIn(jwtTokenProperties.getWebExpireSeconds());
        return loginVO;
    }

    @Override
    public void logout(Long userId) {
        this.redissonTemplate.delete(LazymanConstant.RedisKey.AUTH_USER_PREFIX + userId);
    }

    @Override
    public void sendSmsVerifyCode(String mobile) {
        boolean result = redissonTemplate.tryRateLimiterAcquire(RedisConstant.RATELIMITER_PREFIX + mobile, 1, 60, RateIntervalUnit.SECONDS);
        if (!result) {
            throw new BizException(CommonErrCode.REQUEST_FREQUENTLY);
        }
        String verifyCode = String.valueOf(new Random().nextInt(900000) + 100000);
        SmsTaskFormDTO smsTaskFormDTO = new SmsTaskFormDTO();
        smsTaskFormDTO.setMobiles(mobile);
        smsTaskFormDTO.setTemplateCategory(LazymanConstant.SmsTemplateCategory.SMS_VERIFY_CODE_TEMPLATE);
        JSONObject templateParams = new JSONObject();
        templateParams.set("verifyCode", verifyCode);
        smsTaskFormDTO.setTemplateParams(templateParams);
        iSmsTaskService.createAndSend(smsTaskFormDTO);
        String key = LazymanConstant.RedisKey.AUTH_SMS_VERIFYCODE_PREFIX + mobile;
        redissonTemplate.set(key, verifyCode, LazymanConstant.SMS_VERIFYCODE_EXPIRE_SECONDS, TimeUnit.SECONDS);
        log.info("发送短信验证码:{}", verifyCode);
    }

    @Override
    public void checkSmsVerifyCode(String mobile, String verifyCode) {
        String key = LazymanConstant.RedisKey.AUTH_SMS_VERIFYCODE_PREFIX + mobile;
        String value = redissonTemplate.get(key);
        if (StrUtil.isEmpty(value)) {
            throw new BizException(LazymanErrCode.SMS_VERIFYCODE_INVALID);
        }
        if (!value.equals(verifyCode)) {
            throw new BizException(LazymanErrCode.SMS_VERIFYCODE_ERROR);
        }
        redissonTemplate.delete(key);
        redissonTemplate.deleteRateLimiter(RedisConstant.RATELIMITER_PREFIX + mobile);
    }

    @Override
    public LoginVO loginApp(AppLoginDTO appLoginDTO) {
        AppUser appBuyer = iAppUserService.getOne(Wrappers.<AppUser>query().lambda().eq(StrUtil.isNotBlank(appLoginDTO.getMobile()), AppUser::getMobile, appLoginDTO.getMobile())
                .eq(StrUtil.isNotBlank(appLoginDTO.getOpenId()), AppUser::getOpenId, appLoginDTO.getOpenId()));
        if (ObjectUtil.isEmpty(appBuyer)) {
            appBuyer = new AppUser();
            appBuyer.setMobile(appLoginDTO.getMobile());
            appBuyer.setOpenId(appLoginDTO.getOpenId());
            iAppUserService.save(appBuyer);
        }
        String token = JWTUtils.sign(appBuyer.getId(), jwtTokenProperties.getSecretKey());
        String tokenKey = LazymanConstant.RedisKey.AUTH_USER_PREFIX + appBuyer.getId();
        redissonTemplate.set(tokenKey, token, jwtTokenProperties.getAppExpireSeconds(), TimeUnit.SECONDS);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setExpireIn(jwtTokenProperties.getAppExpireSeconds());
        loginVO.setIsBindMobile(StrUtil.isBlank(appBuyer.getMobile()) ? true : false);
        return loginVO;
    }

    @Override
    public Boolean bindAppMobile(AppLoginDTO appLoginDTO) {
        AppUser appBuyer = iAppUserService.getOne(Wrappers.<AppUser>query().lambda().eq(AppUser::getMobile, appLoginDTO.getMobile()));
        if (ObjectUtil.isNotEmpty(appBuyer)) {
            throw new BizException(LazymanErrCode.MOBILE_EXISTS);
        }
        return iAppUserService.updateByWrapper(Wrappers.<AppUser>update().lambda().eq(AppUser::getId, appLoginDTO.getId())
                .set(
                        AppUser::getMobile, appLoginDTO.getMobile()
                ));
    }
}
