package com.futurenet.sorisoopbackend.subscription.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.subscription.application.SubscriptionService;
import com.futurenet.sorisoopbackend.subscription.domain.SubscriptionPlan;
import com.futurenet.sorisoopbackend.subscription.dto.request.SubscriptionStartRequest;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionResponse;
import com.futurenet.sorisoopbackend.subscription.dto.response.SubscriptionStartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> startSubscription(@RequestBody SubscriptionStartRequest request) {
        Long memberId = 1L; // TODO: 시큐리티
        SubscriptionStartResponse result = subscriptionService.startSubscription(memberId, request);
        return ResponseEntity.ok(new ApiResponse<>("PA100", "구독 시작 성공", result));
    }

    @DeleteMapping
    public ResponseEntity<?> cancelSubscription() {
        Long memberId = 1L; // TODO: 시큐리티
        subscriptionService.cancelSubscription(memberId);
        return ResponseEntity.ok(new ApiResponse<>("PA100", "구독 해지 예약 성공", null));
    }

    @GetMapping("/plans")
    public ResponseEntity<?> getPlans() {
        List<SubscriptionPlan> result = subscriptionService.getSubscriptionPlans();
        return ResponseEntity.ok(new ApiResponse<>("PA100", "요금제 조회 성공", result));
    }


}
