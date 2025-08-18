package com.futurenet.sorisoopbackend.billing.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BillingRepository {
    String getCustomerKeyByMemberId(@Param("memberId") Long memberId);
    void updateCustomerKey(@Param("memberId") Long memberId, @Param("customerKey") String customerKey);
}
