package com.futurenet.sorisoopbackend.payment.application;

import com.futurenet.sorisoopbackend.payment.domain.PaymentRepository;
import com.futurenet.sorisoopbackend.payment.dto.response.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    @Override
    public SubscriptionResponse getSubscription(Long memberId) {
        return paymentRepository.getSubscriptionByMemberId(memberId);
    }
}
