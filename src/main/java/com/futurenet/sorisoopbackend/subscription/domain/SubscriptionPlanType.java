package com.futurenet.sorisoopbackend.subscription.domain;

public enum SubscriptionPlanType {
    MONTH(1),
    YEAR(2);

    private final int durationMonths;

    SubscriptionPlanType(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public int getDurationMonths() {
        return durationMonths;
    }
}
