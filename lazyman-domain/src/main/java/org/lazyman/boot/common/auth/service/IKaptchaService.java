package org.lazyman.boot.common.auth.service;


import org.lazyman.boot.common.auth.vo.KaptchaVO;

/**
 * @author wanglong
 */
public interface IKaptchaService {
    /**
     * 生成图形验证码
     *
     * @return
     */
    KaptchaVO generateKaptcha();

    /**
     * 验证图形验证码
     *
     * @param username
     * @param uuid
     * @param code
     * @return
     */
    void verifyKaptcha(String username, String uuid, String code);
}
