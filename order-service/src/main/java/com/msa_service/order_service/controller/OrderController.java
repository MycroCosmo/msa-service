package com.msa_service.order_service.controller;

import com.msa_service.order_service.dto.OrderRequestDto;
import com.msa_service.order_service.dto.OrderResponseDto;
import com.msa_service.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponseDto> create(@RequestBody @Valid OrderRequestDto dto) {
    return ResponseEntity.ok(orderService.createOrder(dto));
  }
}
