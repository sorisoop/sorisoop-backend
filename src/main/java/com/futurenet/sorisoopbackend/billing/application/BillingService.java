package com.futurenet.sorisoopbackend.billing.application;

import com.futurenet.sorisoopbackend.billing.dto.response.CardStatusResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CreditCardResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerKeyResponse;

import java.util.List;

public interface BillingService {
    // ────────────────────────────────
    // CustomerKey 관련
    // ────────────────────────────────
    CustomerKeyResponse getOrMakeCustomerKey(Long memberId);

    // ────────────────────────────────
    // 카드 관련
    // ────────────────────────────────
    void handleBrandpayAuth(Long memberId, String customerKey, String code); // 카드 등록 전 토큰 발급

    void registerCard(Long memberId);                                        // 카드 등록

    List<CreditCardResponse> getCards(Long memberId);                  // 카드 조회

    CardStatusResponse hasActiveCard(Long memberId);
    void deleteCard(Long memberId, Long cardId);
}