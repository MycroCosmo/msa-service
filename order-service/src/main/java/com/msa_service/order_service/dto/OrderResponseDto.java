package com.msa_service.order_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
  private Long id;
  private Long userId;
  private Long productId;
  private int quantity;
}
