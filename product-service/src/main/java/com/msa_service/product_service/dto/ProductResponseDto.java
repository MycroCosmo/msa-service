package com.msa_service.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDto {
  private Long id;
  private String name;
  private String description;
  private int price;
  private int quantity;
}
