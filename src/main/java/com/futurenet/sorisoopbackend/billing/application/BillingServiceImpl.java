package com.futurenet.sorisoopbackend.billing.application;

import com.futurenet.sorisoopbackend.billing.domain.BillingRepository;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerKeyResponse;
import com.futurenet.sorisoopbackend.billing.util.CustomerKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;

    @Override
    public CustomerKeyResponse getOrMakeCustomerKey(Long memberId) {
        // TODO: 회원 존재 여부 check

        String customerKey = billingRepository.getCustomerKeyByMemberId(memberId);
        if (customerKey != null) return new CustomerKeyResponse(customerKey);

        String newKey = CustomerKeyUtil.generate(memberId);
        billingRepository.updateCustomerKey(memberId, newKey);

        return new CustomerKeyResponse(newKey);
    }
}
