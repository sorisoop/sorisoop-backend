package com.futurenet.sorisoopbackend.payment.application;

import com.futurenet.sorisoopbackend.payment.dto.response.SubscriptionResponse;

public interface PaymentService {
    SubscriptionResponse getSubscription(Long memberId);
}
