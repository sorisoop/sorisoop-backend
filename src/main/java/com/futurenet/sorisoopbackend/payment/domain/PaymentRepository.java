package com.futurenet.sorisoopbackend.payment.domain;

import com.futurenet.sorisoopbackend.payment.dto.response.SubscriptionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentRepository {
    SubscriptionResponse getSubscriptionByMemberId(@Param("memberId") Long memberId);

}
