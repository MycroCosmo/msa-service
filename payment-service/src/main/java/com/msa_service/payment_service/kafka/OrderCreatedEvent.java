package com.msa_service.payment_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {
  private Long orderId;
  private Long userId;
  private Long productId;
  private int quantity;
}
