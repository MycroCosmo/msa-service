package com.msa_service.product_service.kafka;

import com.msa_service.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

  private final ProductService productService;

  @KafkaListener(topics = "order-created", groupId = "product-group", containerFactory = "kafkaListenerContainerFactory")
  public void consume(OrderCreatedEvent event) {
    log.info("Received order-created event: productId={}, quantity={}", event.getProductId(), event.getQuantity());
    productService.decreaseQuantity(event.getProductId(), event.getQuantity());
  }
}
