package com.msa_service.user_service.security;

import com.msa_service.user_service.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secretString;

  private SecretKey secretKey;

  private final long accessTokenValidity = 1000 * 60 * 30; // 30분
  private final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 7; // 7일

  @PostConstruct
  public void init() {
    // JJWT는 최소 256-bit(32 bytes) 키 요구 → UTF-8 인코딩 후 key 생성
    this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
  }


  public String generateAccessToken(User user) {
    return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .claim("role", user.getRole().name())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
  }

  public String generateRefreshToken(User user) {
    return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
  }
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(secretKey)
              .build()
              .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

    return Long.parseLong(claims.getSubject());
  }

}
