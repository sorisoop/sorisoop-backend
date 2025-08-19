package com.futurenet.sorisoopbackend.subscription.dto.response;

import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlanType;
import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private SubscriptionStatus status;
    private SubscriptionPlanType type;
    private LocalDate nextBillingAt;
    private LocalDate cancelledAt;
}
