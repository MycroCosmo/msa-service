package com.msa_service.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
  @NotBlank
  private String name;

  private String description;

  @Min(0)
  private int price;

  @Min(0)
  private int quantity;
}

