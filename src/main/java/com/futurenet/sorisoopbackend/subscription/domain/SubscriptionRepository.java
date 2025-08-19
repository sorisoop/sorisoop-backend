package com.futurenet.sorisoopbackend.subscription.domain;

import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubscriptionRepository {
    SubscriptionResponse getSubscriptionByMemberId(@Param("memberId") Long memberId);

}
