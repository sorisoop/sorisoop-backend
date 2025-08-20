package com.futurenet.sorisoopbackend.paymentHistory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {
    private Long id;
    private Long subscribeId;
    private String orderId;
    private String paymentKey;
    private String method;
    private LocalDateTime paidAt;
    private String status;
    private int amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
}