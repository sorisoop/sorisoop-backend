package com.futurenet.sorisoopbackend.billing.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillingCard {
    private Long id;
    private Long memberId;
    private String methodKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
