package com.futurenet.sorisoopbackend.subscription.application;

import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlan;
import com.futurenet.sorisoopbackend.subscription.dto.request.SubscriptionStartRequest;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionStartResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubscriptionService {
    void handleBrandpayAuth(Long memberId, String customerKey, String code);
    SubscriptionResponse getSubscription(Long memberId);
    List<SubscriptionPlan> getSubscriptionPlans();
    SubscriptionStartResponse startSubscription(Long memberId, SubscriptionStartRequest request);
    SubscriptionStartResponse restartSubscription(Long memberId);
    void cancelSubscription(Long memberId);
}
