package com.msa_service.order_service.service;

import com.msa_service.order_service.domain.Order;
import com.msa_service.order_service.dto.OrderRequestDto;
import com.msa_service.order_service.dto.OrderResponseDto;
import com.msa_service.order_service.kafka.OrderCreatedEvent;
import com.msa_service.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

  @Transactional
  public OrderResponseDto createOrder(OrderRequestDto dto) {
    // 1. 주문 저장
    Order saved = orderRepository.save(Order.builder()
            .userId(dto.getUserId())
            .productId(dto.getProductId())
            .quantity(dto.getQuantity())
            .build());

    // 2. Kafka 메시지 발행 (비동기)
    OrderCreatedEvent event = OrderCreatedEvent.builder()
            .productId(saved.getProductId())
            .quantity(saved.getQuantity())
            .build();

    kafkaTemplate.send("order-created", event)
            .whenComplete((result, ex) -> {
              if (ex != null) {
                log.error("[Kafka] 주문 생성 이벤트 전송 실패", ex);
              } else {
                log.info("[Kafka] 주문 생성 이벤트 전송 성공: {}", result.getProducerRecord());
              }
            });


    // 3. 응답 반환
    return OrderResponseDto.builder()
            .id(saved.getId())
            .userId(saved.getUserId())
            .productId(saved.getProductId())
            .quantity(saved.getQuantity())
            .build();
  }
}
