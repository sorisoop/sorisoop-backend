package com.futurenet.sorisoopbackend.subscription.application;

import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionRepository;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionResponse getSubscription(Long memberId) {
        return subscriptionRepository.getSubscriptionByMemberId(memberId);
    }
}
