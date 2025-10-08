// src/main/java/org/lms/utils/JwtUtil.java
package org.lms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.lms.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import jakarta.annotation.PostConstruct; // 注意导入

@Slf4j
@Component
public class JwtUtil {

    // ⬇️ 静态字段，用于工具类调用
    private static String JWT_KEY;
    private static long JWT_TTL;
    private static SecretKey SECRET_KEY;

    // ⬇️ 用于接收配置（Spring 注入）
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 使用 @PostConstruct 在依赖注入完成后初始化 static 字段
     */
    @PostConstruct
    public void init() {
        JWT_KEY = jwtProperties.getToken();
        JWT_TTL = jwtProperties.getTtl();

        if (JWT_KEY == null || JWT_KEY.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT token 配置不能为空");
        }

        byte[] keyBytes = Base64.getDecoder().decode(JWT_KEY);
        SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");

        log.info("JwtUtil 静态工具类初始化完成，TTL={}ms", JWT_TTL);
    }

    // ==================== 静态工具方法 ====================

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String createJWT(String subject) {
        return createJWT(subject, null);
    }

    public static String createJWT(String subject, Long ttlMillis) {
        long expiration = System.currentTimeMillis() + (ttlMillis != null ? ttlMillis : JWT_TTL);
        Date expDate = new Date(expiration);

        return Jwts.builder()
                .id(getUUID())
                .subject(subject)
                .issuer("sg")
                .issuedAt(new Date())
                .expiration(expDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims parseJWT(String token) throws Exception {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}