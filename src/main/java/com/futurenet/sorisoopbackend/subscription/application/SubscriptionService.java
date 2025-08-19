package com.futurenet.sorisoopbackend.subscription.application;

import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;

public interface SubscriptionService {
    SubscriptionResponse getSubscription(Long memberId);

}
