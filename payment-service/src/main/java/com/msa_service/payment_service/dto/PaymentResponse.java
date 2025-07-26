package com.msa_service.payment_service.dto;

import com.msa_service.payment_service.domain.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
  private Long id;
  private Long orderId;
  private Long userId;
  private int amount;
  private PaymentStatus status;
}

