package com.msa_service.payment_service.service;

import com.msa_service.payment_service.domain.Payment;
import com.msa_service.payment_service.domain.PaymentStatus;
import com.msa_service.payment_service.dto.PaymentRequest;
import com.msa_service.payment_service.kafka.OrderCreatedEvent;
import com.msa_service.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;

  // Kafka 이벤트 기반 자동 결제 처리
  public void processPayment(OrderCreatedEvent event) {
    Payment payment = Payment.builder()
            .orderId(event.getOrderId())
            .userId(event.getUserId())
            .amount(event.getQuantity() * 10000) // 단가 10000원 가정
            .status(PaymentStatus.COMPLETED)
            .build();

    paymentRepository.save(payment);
    log.info("Kafka 기반 결제 처리 완료: {}", payment);
  }

  // REST API 기반 수동 결제 처리
  public Payment processPayment(PaymentRequest request) {
    Payment payment = Payment.builder()
            .orderId(request.getOrderId())
            .userId(request.getUserId())
            .amount(request.getAmount())
            .status(PaymentStatus.COMPLETED)
            .build();

    return paymentRepository.save(payment);
  }
}
