package com.msa_service.product_service.service;

import com.msa_service.product_service.domain.Product;
import com.msa_service.product_service.dto.ProductRequestDto;
import com.msa_service.product_service.dto.ProductResponseDto;
import com.msa_service.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

  private final ProductRepository productRepository;

  public ProductResponseDto createProduct(ProductRequestDto dto) {
    Product saved = productRepository.save(Product.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .quantity(dto.getQuantity())
            .build());

    return toResponseDto(saved);
  }

  public List<ProductResponseDto> getAllProducts() {
    return productRepository.findAll()
            .stream()
            .map(this::toResponseDto)
            .toList();
  }

  public ProductResponseDto getProduct(Long id) {
    Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    return toResponseDto(product);
  }

  public void decreaseQuantity(Long productId, int amount) {
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    if (product.getQuantity() < amount) {
      throw new IllegalArgumentException("재고가 부족합니다.");
    }
    product.decrease(amount);
  }

  private ProductResponseDto toResponseDto(Product product) {
    return ProductResponseDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .build();
  }

}

