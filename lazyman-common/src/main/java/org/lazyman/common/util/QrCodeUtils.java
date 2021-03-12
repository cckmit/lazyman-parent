package org.lazyman.common.util;

import cn.hutool.core.codec.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.lazyman.common.constant.StringPool;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public final class QrCodeUtils {

    public static String createQRCode(String content, int width, int height, String imageType) throws Exception {
        /**
         * 定义二维码的参数
         */
        HashMap hints = new HashMap();
        //指定字符编码为“utf-8”
        hints.put(EncodeHintType.CHARACTER_SET, StringPool.UTF_8);
        //指定二维码的纠错等级为中级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置图片的边距
        hints.put(EncodeHintType.MARGIN, 2);
        /**
         * 生成二维码
         */
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, imageType, byteArrayOutputStream);
        String imgString = Base64.encode(byteArrayOutputStream.toByteArray());
        return imgString;
    }
}
