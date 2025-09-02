package com.futurenet.sorisoopbackend.subscription.domain;

import com.futurenet.sorisoopbackend.brandpaytoken.dto.response.CustomerTokenResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubscriptionRepository {
    Subscription getSubscriptionByMemberId(@Param("memberId") Long memberId);
    List<SubscriptionPlan> getSubscriptionPlans();
    SubscriptionPlan getSubscriptionPlanByType(@Param("planType") String planType);
    SubscriptionPlan getSubscriptionPlanById(@Param("planId") Long planId);
    int insertSubscription(Subscription subscription);
    int cancelSubscription(@Param("memberId") Long memberId);
    int updateSubscriptionStatus(@Param("subscriptionId") Long subscriptionId,
                                 @Param("status") SubscriptionStatus status,
                                 @Param("updatedBy") Long updatedBy);
    int insertCustomerToken(@Param("memberId") Long memberId, @Param("token") CustomerTokenResponse token);
    int updateCustomerToken(@Param("memberId") Long memberId, @Param("token") CustomerTokenResponse token);
}
