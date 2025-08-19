package com.futurenet.sorisoopbackend.payment.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.payment.application.PaymentService;
import com.futurenet.sorisoopbackend.payment.dto.response.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/subscription")
    public ResponseEntity<?> getSubscription() {
        Long memberId = 1L; // TODO: 시큐리티
        SubscriptionResponse result = paymentService.getSubscription(memberId);
        return ResponseEntity.ok(new ApiResponse<>("PA100", "구독 조회 성공", result));
    }
}
