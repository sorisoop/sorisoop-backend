package com.futurenet.sorisoopbackend.subscription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private Long id;
    private Long memberId;
    private Long subscriptionPlanId;
    private SubscriptionStatus status;
    private LocalDateTime startedAt;
    private LocalDate nextBillingAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime lastApprovedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
