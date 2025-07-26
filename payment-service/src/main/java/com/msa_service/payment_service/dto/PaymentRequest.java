package com.msa_service.payment_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
  private Long orderId;
  private Long userId;
  private int amount;
}

