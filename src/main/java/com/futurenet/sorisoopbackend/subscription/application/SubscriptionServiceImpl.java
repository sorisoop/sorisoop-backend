package com.futurenet.sorisoopbackend.subscription.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.futurenet.sorisoopbackend.brandPayToken.dto.response.CustomerTokenResponse;
import com.futurenet.sorisoopbackend.member.domain.MemberRepository;
import com.futurenet.sorisoopbackend.paymenthistory.application.exception.PaymentHistoryErrorCode;
import com.futurenet.sorisoopbackend.paymenthistory.application.exception.PaymentHistoryException;
import com.futurenet.sorisoopbackend.paymenthistory.domain.PaymentHistory;
import com.futurenet.sorisoopbackend.paymenthistory.domain.PaymentHistoryRepository;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubscriptionErrorCode;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubscriptionException;
import com.futurenet.sorisoopbackend.subscription.domain.*;
import com.futurenet.sorisoopbackend.subscription.dto.request.SubscriptionStartRequest;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionStartResponse;
import com.futurenet.sorisoopbackend.subscription.infrastructure.TossClient;
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
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final MemberRepository memberRepository;
    private final TossClient tossClient;

    @Override
    @Transactional
    public void handleBrandpayAuth(Long memberId, String customerKey, String code) {

        CustomerTokenResponse response = tossClient.issueCustomerToken(customerKey, code);
        response.setExpiresAt(LocalDateTime.now().plusSeconds(response.getExpiresIn()));

        String existingKey = memberRepository.getCustomerKeyByMemberId(memberId);

        int affectedRows = (existingKey == null)
                ? subscriptionRepository.insertCustomerToken(memberId, response)
                : subscriptionRepository.updateCustomerToken(memberId, response);

        if (affectedRows <= 0) {
            throw new SubscriptionException(SubscriptionErrorCode.CUSTOMER_TOKEN_SAVE_FAIL);
        }
    }

    @Override
    @Transactional
    public SubscriptionResponse getSubscription(Long memberId) {
        Subscription subscription = subscriptionRepository.getSubscriptionByMemberId(memberId);
        if (subscription == null) return null;

        String planType = subscriptionRepository.
                getSubscriptionPlanById(subscription.getSubscriptionPlanId())
                .getType();

        return new SubscriptionResponse(
                subscription.getStatus().name(),
                planType,
                subscription.getStartedAt(),
                subscription.getNextBillingAt().atStartOfDay(),
                subscription.getCancelledAt(),
                subscription.getLastApprovedAt()
        );
    }

    @Override
    @Transactional
    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionRepository.getSubscriptionPlans();
    }

    @Override
    @Transactional
    public SubscriptionStartResponse startSubscription(Long memberId, SubscriptionStartRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Subscription existing = subscriptionRepository.getSubscriptionByMemberId(memberId);
        if (existing != null && SubscriptionStatus.ACTIVE.equals(existing.getStatus())) {
            throw new SubscriptionException(SubscriptionErrorCode.ALREADY_ACTIVE);
        }

        SubscriptionPlan plan = Optional.ofNullable(
                subscriptionRepository.getSubscriptionPlanByType(request.getPlanType().name())
        ).orElseThrow(() -> new SubscriptionException(SubscriptionErrorCode.NOT_FOUND_PLAN));

        JsonNode paymentResult = tossClient.confirmPayment(
                request.getPaymentKey(),
                request.getOrderId(),
                request.getAmount()
        );

        String paymentKey = paymentResult.get("paymentKey").asText();
        String orderId = paymentResult.get("orderId").asText();
        LocalDateTime approvedAt = OffsetDateTime.parse(paymentResult.get("approvedAt").asText()).toLocalDateTime();
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
            throw new SubscriptionException(SubscriptionErrorCode.SUBSCRIPTION_CREATE_FAIL);
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

    @Override
    @Transactional
    public SubscriptionStartResponse restartSubscription(Long memberId) {
        LocalDate today = LocalDate.now();

        Subscription subscription = Optional.ofNullable(
                subscriptionRepository.getSubscriptionByMemberId(memberId)
        ).orElseThrow(() -> new SubscriptionException(SubscriptionErrorCode.NOT_FOUND_SUBSCRIPTION));

        if (!SubscriptionStatus.CANCELLED.equals(subscription.getStatus())) {
            throw new SubscriptionException(SubscriptionErrorCode.INVALID_STATUS);
        }

        SubscriptionPlan plan = Optional.ofNullable(
                subscriptionRepository.getSubscriptionPlanById(subscription.getSubscriptionPlanId())
        ).orElseThrow(() -> new SubscriptionException(SubscriptionErrorCode.NOT_FOUND_PLAN));
        LocalDate nextBillingAt = subscription.getNextBillingAt();

        if (!nextBillingAt.isAfter(today)) {
            throw new SubscriptionException(SubscriptionErrorCode.RESTART_REQUIRES_PAYMENT);
        }

        int updated = subscriptionRepository.updateSubscriptionStatus(
                subscription.getId(),
                SubscriptionStatus.ACTIVE,
                memberId
        );

        if (updated <= 0) {
            throw new SubscriptionException(SubscriptionErrorCode.UPDATE_STATUS_FAIL);
        }

        return new SubscriptionStartResponse(SubscriptionStatus.ACTIVE, plan.getPlanTypeEnum(), nextBillingAt);
    }

    @Override
    @Transactional
    public void cancelSubscription(Long memberId) {
        int updated = subscriptionRepository.cancelSubscription(memberId);
        if (updated <= 0) {
            throw new SubscriptionException(SubscriptionErrorCode.CANCEL_FAIL);
        }
    }

    @Override
    public boolean isSubscribed(Long memberId) {
        Subscription subscription = subscriptionRepository.getSubscriptionByMemberId(memberId);

        if (subscription == null) {
            return false;
        }

        LocalDate nextBillingAt = subscription.getNextBillingAt();
        return nextBillingAt.isAfter(LocalDate.now());
    }
}
