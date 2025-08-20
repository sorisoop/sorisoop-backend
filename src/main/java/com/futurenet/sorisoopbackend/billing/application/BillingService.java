package com.futurenet.sorisoopbackend.billing.application;

import com.futurenet.sorisoopbackend.billing.dto.response.CustomerKeyResponse;


public interface BillingService {
    CustomerKeyResponse getOrMakeCustomerKey(Long memberId);
    void handleBrandpayAuth(Long memberId, String customerKey, String code);
}