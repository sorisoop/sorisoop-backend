package com.futurenet.sorisoopbackend.subscription.application;

import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlan;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {
    SubscriptionResponse getSubscription(Long memberId);
    List<SubscriptionPlan> getSubscriptionPlans();
}
