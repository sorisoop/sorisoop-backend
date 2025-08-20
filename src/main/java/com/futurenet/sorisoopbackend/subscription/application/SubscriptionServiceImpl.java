package com.futurenet.sorisoopbackend.subscription.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.futurenet.sorisoopbackend.billing.application.exception.BillingErrorCode;
import com.futurenet.sorisoopbackend.billing.application.exception.BillingException;
import com.futurenet.sorisoopbackend.billing.domain.BillingCard;
import com.futurenet.sorisoopbackend.billing.domain.BillingRepository;
import com.futurenet.sorisoopbackend.billing.infrastructure.TossClient;
import com.futurenet.sorisoopbackend.paymentHistory.application.exception.PaymentHistoryErrorCode;
import com.futurenet.sorisoopbackend.paymentHistory.application.exception.PaymentHistoryException;
import com.futurenet.sorisoopbackend.paymentHistory.domain.PaymentHistory;
import com.futurenet.sorisoopbackend.paymentHistory.domain.PaymentHistoryRepository;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubScriptionException;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubscriptionErrorCode;
import com.futurenet.sorisoopbackend.subscription.domain.*;
import com.futurenet.sorisoopbackend.subscription.dto.request.SubscriptionStartRequest;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionStartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final BillingRepository billingRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final TossClient tossClient;

    @Override
    public SubscriptionResponse getSubscription(Long memberId) {
        return subscriptionRepository.getSubscriptionByMemberId(memberId);
    }

    @Override
    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionRepository.getSubscriptionPlans();
    }

    @Override
    @Transactional
    public SubscriptionStartResponse startSubscription(Long memberId, SubscriptionStartRequest request) {
        LocalDateTime now = LocalDateTime.now();

        SubscriptionPlan plan = Optional.ofNullable(
                subscriptionRepository.getSubscriptionPlanByType(request.getPlanType().name())
        ).orElseThrow(() -> new SubScriptionException(SubscriptionErrorCode.NOT_FOUND_PLAN));

        JsonNode paymentResult = tossClient.confirmPayment(
                request.getPaymentKey(),
                request.getOrderId(),
                request.getAmount()
        );

        String paymentKey = paymentResult.get("paymentKey").asText();
        String orderId = paymentResult.get("orderId").asText();
        LocalDateTime approvedAt = OffsetDateTime.parse(paymentResult.get("approvedAt").asText())
                .toLocalDateTime();
        String method = paymentResult.get("method").asText();
        int amount = paymentResult.get("totalAmount").asInt();
        String paymentStatus = paymentResult.get("status").asText();

        SubscriptionStatus status = SubscriptionStatus.ACTIVE;
        SubscriptionPlanType type = SubscriptionPlanType.valueOf(plan.getType());
        LocalDate nextBillingAt = now.toLocalDate().plusMonths(
                type == SubscriptionPlanType.MONTH ? 1 : 12
        );

        Subscription subscription = Subscription.builder()
                .memberId(memberId)
                .subscriptionPlanId(plan.getId())
                .status(status)
                .startedAt(now)
                .nextBillingAt(nextBillingAt)
                .lastApprovedAt(approvedAt)
                .createdAt(now)
                .createdBy(memberId)
                .build();

        int subCount = subscriptionRepository.insertSubscription(subscription);
        if (subCount <= 0) {
            throw new SubScriptionException(SubscriptionErrorCode.SUBSCRIPTION_CREATE_FAIL);
        }

        PaymentHistory history = PaymentHistory.builder()
                .subscribeId(subscription.getId())
                .orderId(orderId)
                .paymentKey(paymentKey)
                .method(method)
                .paidAt(approvedAt)
                .status(paymentStatus)
                .amount(amount)
                .createdAt(now)
                .createdBy(memberId)
                .build();

        int payCount = paymentHistoryRepository.insertPaymentHistory(history);
        if (payCount <= 0) throw new PaymentHistoryException(PaymentHistoryErrorCode.PAYMENT_HISTORY_CREATE_FAIL);

        return new SubscriptionStartResponse(status, type, nextBillingAt);
    }
}
