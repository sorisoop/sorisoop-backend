package com.futurenet.sorisoopbackend.billing.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BillingRepository {
    String getCustomerKeyByMemberId(@Param("memberId") Long memberId);
    void updateCustomerKey(@Param("memberId") Long memberId, @Param("customerKey") String customerKey);
    int hasActiveCard(@Param("memberId") Long memberId);
    void deactivateCards(@Param("memberId") Long memberId);
    void insertBillingCard(
            @Param("memberId") Long memberId,
            @Param("billingKey") String billingKey,
            @Param("cardCompany") String cardCompany,
            @Param("cardNumber") String cardNumber
    );
    int existsCard(@Param("memberId") Long memberId, @Param("issuerName") String issuerName, @Param("cardNumber") String cardNumber);
}
