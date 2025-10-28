package com.example.spring04.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    // ⚠️ 너무 짧은 키는 에러가 납니다. 최소 32바이트 이상 문자열이어야 합니다.
    private static final String SECRET_KEY = "my-super-secure-qr-secret-key-2025-example"; 

    // ✅ Key 객체로 변환
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * ✅ 토큰 생성
     * @param empId 직원 ID
     * @param type "checkin" 또는 "checkout"
     * @param expireSeconds 유효시간(초)
     */
    public String createToken(String empId, String type, int expireSeconds) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireSeconds * 1000L);

        return Jwts.builder()
                .setSubject(empId)
                .claim("empId", empId)
                .claim("type", type)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * ✅ 출근 전용 기본 토큰 (type=checkin)
     */
    public String createToken(String empId) {
        return createToken(empId, "checkin", 60);
    }

    /**
     * ✅ 토큰 검증 및 데이터 추출
     */
    public Map<String, String> validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Map<String, String> data = new HashMap<>();
            data.put("empId", claims.get("empId", String.class));
            data.put("type", claims.get("type", String.class));
            return data;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new RuntimeException("토큰 검증 실패: " + e.getMessage());
        }
    }

    /**
     * ✅ 토큰 유효성만 단순 검사 (boolean)
     */
    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * ✅ empId 단독 추출
     */
    public String getEmpId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("empId", String.class);
    }
}
