package com.msa_service.payment_service.kafka;

import com.msa_service.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

  private final PaymentService paymentService;

  @KafkaListener(topics = "order-created", groupId = "payment-group")
  public void consume(OrderCreatedEvent event) {
    log.info("[Kafka] Received order-created: orderId={}, userId={}, productId={}, quantity={}",
            event.getOrderId(), event.getUserId(), event.getProductId(), event.getQuantity());
    paymentService.processPayment(event);
  }
}
