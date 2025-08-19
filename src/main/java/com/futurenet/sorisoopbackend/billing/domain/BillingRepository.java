package com.futurenet.sorisoopbackend.billing.domain;

import com.futurenet.sorisoopbackend.billing.dto.response.BrandPayCardResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CreditCardResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerTokenResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillingRepository {
    String getCustomerKeyByMemberId(@Param("memberId") Long memberId);
    void updateCustomerKey(@Param("memberId") Long memberId, @Param("customerKey") String customerKey);
    int hasActiveCard(@Param("memberId") Long memberId);
    void deactivateCards(@Param("memberId") Long memberId);
    int existsCard(@Param("memberId") Long memberId, @Param("methodKey") String methodKey);
    CustomerTokenResponse getCustomerTokenByMemberId(@Param("memberId") Long memberId);
    void insertCustomerToken(@Param("memberId") Long memberId, @Param("token") CustomerTokenResponse token);
    void updateCustomerToken(@Param("memberId") Long memberId, @Param("token") CustomerTokenResponse token);
    List<CreditCardResponse> getCardsByMemberId(@Param("memberId") Long memberId);
    void insertCard(@Param("memberId") Long memberId, @Param("card") BrandPayCardResponse card);
    BillingCard getCardById(@Param("memberId") Long memberId, @Param("cardId") Long cardId);
    int deleteCard(@Param("memberId") Long memberId, @Param("cardId") Long cardId);

}
