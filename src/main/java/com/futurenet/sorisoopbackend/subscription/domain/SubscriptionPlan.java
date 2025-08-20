package com.futurenet.sorisoopbackend.subscription.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlan {
    private Long id;
    private String type;
    private int price;

    public int getDurationMonths() {
        return SubscriptionPlanType.valueOf(type).getDurationMonths();
    }

    public SubscriptionPlanType getPlanTypeEnum() {
        return SubscriptionPlanType.valueOf(type);
    }

}
