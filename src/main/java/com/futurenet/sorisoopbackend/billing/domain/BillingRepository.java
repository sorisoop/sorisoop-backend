package com.futurenet.sorisoopbackend.billing.domain;

import com.futurenet.sorisoopbackend.billing.dto.response.BrandPayCardResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerTokenResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillingRepository {
    String getCustomerKeyByMemberId(@Param("memberId") Long memberId);
    void updateCustomerKey(@Param("memberId") Long memberId, @Param("customerKey") String customerKey);
    CustomerTokenResponse getCustomerTokenByMemberId(@Param("memberId") Long memberId);
    void insertCustomerToken(@Param("memberId") Long memberId, @Param("token") CustomerTokenResponse token);
    void updateCustomerToken(@Param("memberId") Long memberId, @Param("token") CustomerTokenResponse token);
}
