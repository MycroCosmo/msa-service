package com.msa_service.product_service.controller;

import com.msa_service.product_service.dto.ProductQuantityRequestDto;
import com.msa_service.product_service.dto.ProductRequestDto;
import com.msa_service.product_service.dto.ProductResponseDto;
import com.msa_service.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductRequestDto dto) {
    return ResponseEntity.ok(productService.createProduct(dto));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> getAll() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDto> getOne(@PathVariable Long id) {
    return ResponseEntity.ok(productService.getProduct(id));
  }

  @PatchMapping("/{id}/decrease")
  public ResponseEntity<Void> decrease(
          @PathVariable Long id,
          @RequestBody ProductQuantityRequestDto dto
  ) {
    productService.decreaseQuantity(id, dto.getAmount());
    return ResponseEntity.ok().build();
  }

}

