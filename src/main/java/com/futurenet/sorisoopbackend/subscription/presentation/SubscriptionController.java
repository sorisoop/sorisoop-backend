package com.futurenet.sorisoopbackend.subscription.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.subscription.application.SubscriptionService;
import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlan;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<?> getSubscription() {
        Long memberId = 1L; // TODO: 시큐리티
        SubscriptionResponse result = subscriptionService.getSubscription(memberId);
        return ResponseEntity.ok(new ApiResponse<>("PA100", "구독 조회 성공", result));
    }

    @GetMapping("/plans")
    public ResponseEntity<?> getPlans() {
        List<SubscriptionPlan> result = subscriptionService.getSubscriptionPlans();
        return ResponseEntity.ok(new ApiResponse<>("PA100", "요금제 조회 성공", result));
    }
}
