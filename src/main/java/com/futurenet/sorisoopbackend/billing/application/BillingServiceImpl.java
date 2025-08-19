package com.futurenet.sorisoopbackend.billing.application;

import com.futurenet.sorisoopbackend.billing.application.exception.BillingErrorCode;
import com.futurenet.sorisoopbackend.billing.application.exception.BillingException;
import com.futurenet.sorisoopbackend.billing.domain.BillingRepository;
import com.futurenet.sorisoopbackend.billing.dto.response.*;
import com.futurenet.sorisoopbackend.billing.infrastructure.TossClient;
import com.futurenet.sorisoopbackend.billing.util.CustomerKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final TossClient tossClient;

    @Override
    public CustomerKeyResponse getOrMakeCustomerKey(Long memberId) {
        String customerKey = billingRepository.getCustomerKeyByMemberId(memberId);
        if (customerKey != null) return new CustomerKeyResponse(customerKey);

        String newKey = CustomerKeyUtil.generate(memberId);
        billingRepository.updateCustomerKey(memberId, newKey);

        return new CustomerKeyResponse(newKey);
    }

    @Override
    @Transactional
    public void handleBrandpayAuth(Long memberId, String customerKey, String code) {
        CustomerTokenResponse response = tossClient.issueCustomerToken(customerKey, code);

        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(response.getExpiresIn());
        response.setExpiresAt(expiresAt);

        if (billingRepository.getCustomerTokenByMemberId(memberId) == null) {
            billingRepository.insertCustomerToken(memberId, response);
            return;
        }

        billingRepository.updateCustomerToken(memberId, response);
    }

    @Override
    @Transactional
    public void registerCard(Long memberId) {
        CustomerTokenResponse token = billingRepository.getCustomerTokenByMemberId(memberId);
        if (token == null) throw new BillingException(BillingErrorCode.NOT_FOUND_CUSTOMER_TOKEN);

        String customerKey = billingRepository.getCustomerKeyByMemberId(memberId);
        List<BrandPayCardResponse> creditCards =
                tossClient.getPaymentMethods(customerKey, token.getAccessToken());

        billingRepository.deactivateCards(memberId);

        creditCards.forEach(card -> {
            if (billingRepository.existsCard(memberId, card.getMethodKey()) == 0) {
                billingRepository.insertCard(memberId, card);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCardResponse> getCreditCards(Long memberId) {
        return billingRepository.getCreditCardsByMemberId(memberId);
    }

    @Override
    public CardStatusResponse hasActiveCard(Long memberId) {
        int count = billingRepository.hasActiveCard(memberId);
        boolean exists = count > 0;
        return new CardStatusResponse(exists);
    }
}
