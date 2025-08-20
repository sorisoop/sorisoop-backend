package com.futurenet.sorisoopbackend.subscription.dto.request;

import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlanType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscriptionStartRequest {
    private String orderId;
    private String paymentKey;
    private int amount;
    private SubscriptionPlanType planType;
}
