package org.lazyman.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class JWTUtils {
    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_TENANT_ID = "tenantId";
    public static final String CLAIM_TIMESTAMP = "timestamp";

    public static String sign(Long userId, Long tenantId, String secretKey) {
        if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(tenantId)) {
            return null;
        }
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withClaim(CLAIM_USER_ID, userId)
                .withClaim(CLAIM_TENANT_ID, tenantId)
                .withClaim(CLAIM_TIMESTAMP, System.currentTimeMillis())
                .sign(algorithm);
    }

    public static String sign(Long userId, String secretKey) {
        return sign(userId, 0L, secretKey);
    }

    public static boolean verify(String token, Long userId, Long tenantId, String secretKey) {
        if (StrUtil.isEmpty(token) || ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(tenantId)) {
            return false;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(CLAIM_USER_ID, userId)
                    .withClaim(CLAIM_TENANT_ID, tenantId)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean verify(String token, Long userId, String secretKey) {
        return verify(token, userId, 0L, secretKey);
    }

    public static Long parseUserId(String token) {
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        try {
            DecodedJWT decodedJwt = JWT.decode(token);
            return decodedJwt.getClaim(CLAIM_USER_ID).asLong();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long parseTenantId(String token) {
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        try {
            DecodedJWT decodedJwt = JWT.decode(token);
            return decodedJwt.getClaim(CLAIM_TENANT_ID).asLong();
        } catch (Exception e) {
            return null;
        }
    }
}
