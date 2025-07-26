package com.msa_service.product_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private int price;

  private int quantity;

  // === 수량 감소 메서드 ===
  public void decrease(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("감소할 수량은 1 이상이어야 합니다.");
    }

    if (this.quantity < amount) {
      throw new IllegalArgumentException("재고가 부족합니다. 현재 수량: " + this.quantity);
    }

    this.quantity -= amount;
  }
}

