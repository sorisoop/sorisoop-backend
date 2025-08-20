package com.futurenet.sorisoopbackend.subscription.domain;

import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubscriptionRepository {
    SubscriptionResponse getSubscriptionByMemberId(@Param("memberId") Long memberId);
    List<SubscriptionPlan> getSubscriptionPlans();
    SubscriptionPlan getSubscriptionPlanByType(@Param("planType") String planType);
    int insertSubscription(Subscription subscription);
    void cancelSubscription(@Param("memberId") Long memberId);
}
