package org.lazyman.boot.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.lazyman.boot.auth.service.IKaptchaService;
import org.lazyman.boot.auth.vo.KaptchaVO;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.common.constant.LazymanErrCode;
import org.lazyman.boot.sys.service.ISysLoginLogService;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.UUIDUtils;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

@Service
public class KaptchaServiceImpl implements IKaptchaService {
    public static final String IMG_BASE64_HEADER = "data:image/png;base64,";
    public static final String IMG_FORMAT_PNG = "png";

    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedissonTemplate redissonTemplate;
    @Resource
    private ISysLoginLogService iSysLoginLogService;

    @Override
    public KaptchaVO generateKaptcha() {
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);
        String base64Content = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, IMG_FORMAT_PNG, byteArrayOutputStream);
            base64Content = Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, e);
        }
        String img = IMG_BASE64_HEADER + base64Content;
        String nonce = UUIDUtils.getShortUUID();
        String key = LazymanConstant.RedisKey.AUTH_KAPTCHA_PREFIX + nonce;
        redissonTemplate.set(key, text, LazymanConstant.KAPTCHA_VERIFYCODE_EXPIRE_SECONDS, TimeUnit.SECONDS);
        KaptchaVO kaptchaVO = new KaptchaVO();
        kaptchaVO.setImg(img);
        kaptchaVO.setUuid(nonce);
        return kaptchaVO;
    }

    @Override
    public void verifyKaptcha(String username, String nonce, String kaptcha) {
        String key = LazymanConstant.RedisKey.AUTH_KAPTCHA_PREFIX + nonce;
        String value = redissonTemplate.get(key);
        if (StrUtil.isEmpty(value)) {
            iSysLoginLogService.save(username, false, LazymanErrCode.KAPTCHA_INVALID.getMessage());
            throw new BizException(LazymanErrCode.KAPTCHA_INVALID);
        }
        if (!value.equalsIgnoreCase(kaptcha)) {
            iSysLoginLogService.save(username, false, LazymanErrCode.KAPTCHA_ERROR.getMessage());
            throw new BizException(LazymanErrCode.KAPTCHA_ERROR);
        }
        redissonTemplate.delete(key);
    }
}
