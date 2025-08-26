package com.futurenet.sorisoopbackend.subscription.aop;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.subscription.application.SubscriptionService;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubscriptionErrorCode;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubscriptionException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckSubscriptionAspect {

    private final SubscriptionService subscriptionService;

    @Before("@annotation(CheckSubscription)")
    public void checkSubscription(JoinPoint joinPoint) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!subscriptionService.isSubscribed(userPrincipal.getId())) {
            throw new SubscriptionException(SubscriptionErrorCode.INACTIVE_SUBSCRIPTION);
        }
    }
}
