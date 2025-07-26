package com.msa_service.payment_service.kafka;

import com.msa_service.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventConsumer {

  private final PaymentService paymentService;

  @KafkaListener(topics = "order-created", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactory")
  public void consume(OrderCreatedEvent event) {
    log.info("결제 서비스에서 주문 이벤트 수신: orderId={}, userId={}, quantity={}", event.getOrderId(), event.getUserId(), event.getQuantity());
    paymentService.processPayment(event);
  }
}
