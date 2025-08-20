package com.futurenet.sorisoopbackend.subscription.application;

import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlan;
import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlanType;
import com.futurenet.sorisoopbackend.subscription.dto.request.SubscriptionStartRequest;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionStartResponse;

import java.util.List;

public interface SubscriptionService {
    SubscriptionResponse getSubscription(Long memberId);
    List<SubscriptionPlan> getSubscriptionPlans();
    SubscriptionStartResponse startSubscription(Long memberId, SubscriptionStartRequest request);

}
