package com.futurenet.sorisoopbackend.billing.application;

import com.futurenet.sorisoopbackend.billing.domain.BillingRepository;
import com.futurenet.sorisoopbackend.billing.dto.response.*;
import com.futurenet.sorisoopbackend.billing.infrastructure.TossClient;
import com.futurenet.sorisoopbackend.billing.util.CustomerKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}
