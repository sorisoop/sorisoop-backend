package com.futurenet.sorisoopbackend.billing.application;

import com.futurenet.sorisoopbackend.billing.application.exception.BillingErrorCode;
import com.futurenet.sorisoopbackend.billing.application.exception.BillingException;
import com.futurenet.sorisoopbackend.billing.domain.BillingRepository;
import com.futurenet.sorisoopbackend.billing.domain.CardCompany;
import com.futurenet.sorisoopbackend.billing.dto.response.BillingKeyResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CardStatusResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerKeyResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.PaymentMethodsResponse;
import com.futurenet.sorisoopbackend.billing.infrastructure.TossClient;
import com.futurenet.sorisoopbackend.billing.util.CustomerKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public CardStatusResponse hasActiveCard(Long memberId) {
        int count = billingRepository.hasActiveCard(memberId);
        boolean exists = count > 0;
        return new CardStatusResponse(exists);
    }

    @Override
    @Transactional
    public void registerCard(Long memberId, String customerKey, String authKey) {
        BillingKeyResponse billingKeyResponse = tossClient.issueBillingKey(customerKey, authKey);
        String issuerCode = billingKeyResponse.getCard().getIssuerCode();
        String maskedNumber = billingKeyResponse.getCard().getNumber();
        CardCompany company = CardCompany.fromCode(issuerCode);
        String issuerName = company.getKoreanName();

        int count = billingRepository.existsCard(memberId, issuerName, maskedNumber);
        if (count > 0) throw new BillingException(BillingErrorCode.DUPLICATE_CARD);

        billingRepository.deactivateCards(memberId);
        billingRepository.insertBillingCard(
                memberId,
                billingKeyResponse.getBillingKey(),
                company.getKoreanName(),
                billingKeyResponse.getCard().getNumber()
        );
    }
}
