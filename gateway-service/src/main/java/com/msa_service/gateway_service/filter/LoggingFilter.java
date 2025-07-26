package com.msa_service.gateway_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
    String path = exchange.getRequest().getPath().toString();
    log.info("[Request] Path: {}", path);

    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
      log.info("[Response] Completed for Path: {}", path);
    }));
  }

  @Override
  public int getOrder() {
    return 0; // 필터 순서 지정 (낮을수록 먼저 실행)
  }
}
