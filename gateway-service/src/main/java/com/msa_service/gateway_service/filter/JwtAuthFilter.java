package com.msa_service.gateway_service.filter;

import com.msa_service.gateway_service.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter {

  private final JwtTokenProvider tokenProvider;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String token = extractToken(exchange.getRequest().getHeaders());

    if (token == null || !tokenProvider.validateToken(token)) {
      log.warn("Unauthorized request - missing or invalid token");
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    Claims claims = tokenProvider.getClaims(token);
    String userId = claims.getSubject();

    // 사용자 정보 헤더에 주입
    ServerWebExchange mutated = exchange.mutate()
            .request(r -> r.header("X-User-Id", userId))
            .build();

    return chain.filter(mutated);
  }

  private String extractToken(HttpHeaders headers) {
    String bearer = headers.getFirst(HttpHeaders.AUTHORIZATION);
    return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
  }
}
