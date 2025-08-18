package com.futurenet.sorisoopbackend.billing.application;

import com.futurenet.sorisoopbackend.billing.dto.response.CardStatusResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerKeyResponse;

public interface BillingService {
    CustomerKeyResponse getOrMakeCustomerKey(Long memberId);
    CardStatusResponse hasActiveCard(Long memberId);
}
