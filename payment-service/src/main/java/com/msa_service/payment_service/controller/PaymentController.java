package com.msa_service.payment_service.controller;

import com.msa_service.payment_service.domain.Payment;
import com.msa_service.payment_service.dto.PaymentRequest;
import com.msa_service.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping
  public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest request) {
    Payment result = paymentService.processPayment(request);
    return ResponseEntity.ok(result);
  }
}

