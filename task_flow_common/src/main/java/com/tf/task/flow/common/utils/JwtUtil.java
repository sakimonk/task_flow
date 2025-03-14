package com.tf.task.flow.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author ouweijian
 * @date 2025/3/12 14:06
 */
public class JwtUtil {

    // 固定的 Base64 编码密钥字符串
    private static final String SECRET_KEY_STRING = "AyCXjKhhL703myzN1Xh9uprZt83u8ra5fCknUj_b3_A="; // 长度要符合算法要求
    // 从固定密钥字符串生成 SecretKey
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24小时

    /**
     * 生成 JWT Token
     * @param subject 用户信息
     * @return JWT Token
     */
    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject) // 设置用户标识
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(SECRET_KEY) // 使用密钥和加密算法签名
                .compact();
    }

    /**
     * 解析 Token 并提取 Claims
     * @param token JWT Token
     * @return Claims
     */
    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // 设置签名密钥
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 解析 JWT Token
    public static String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

//    public static void main(String[] args) {
//        String s = extractUserId("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjozLFwiZGV2aWNlTm9cIjpcIlwifSIsImlhdCI6MTczNzM3NzIyMywiZXhwIjoxNzM3NDYzNjIzfQ.g9tfGpelHuf8iM8j5VvVZIMYufnDH9svYkB627gL07s");
//        System.out.println(s);
//    }

    /**
     * 检查 Token 是否已过期
     * @param token JWT Token
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
