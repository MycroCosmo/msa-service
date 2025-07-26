package com.msa_service.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

  @NotNull(message = "userId는 필수입니다.")
  private Long userId;

  @NotNull(message = "productId는 필수입니다.")
  private Long productId;

  @Min(value = 1, message = "수량은 최소 1 이상이어야 합니다.")
  private int quantity;
}
