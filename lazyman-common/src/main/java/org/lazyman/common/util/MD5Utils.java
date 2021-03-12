package org.lazyman.common.util;

import org.lazyman.common.constant.StringPool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Utils {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encode(byte[] bytes) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(StringPool.MD5);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        messageDigest.update(bytes);
        byte[] updateBytes = messageDigest.digest();
        int len = updateBytes.length;
        char[] newChar = new char[len * 2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            byte byte0 = updateBytes[i];
            newChar[(k++)] = HEX_DIGITS[(byte0 >>> 4 & 0xF)];
            newChar[(k++)] = HEX_DIGITS[(byte0 & 0xF)];
        }
        return new String(newChar);
    }

    public static String encode(String plainText) {
        try {
            return encode(plainText.getBytes(StringPool.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
