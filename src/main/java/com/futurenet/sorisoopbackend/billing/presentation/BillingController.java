package com.futurenet.sorisoopbackend.billing.presentation;

import com.futurenet.sorisoopbackend.billing.application.BillingService;
import com.futurenet.sorisoopbackend.billing.dto.response.CardStatusResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerKeyResponse;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {
    private final BillingService billingService;

    /**
     * 고객 키(Customer Key) 조회/생성 API
     * - 회원 단위로 1개의 키만 관리
     * - 이미 존재하면 조회, 없으면 새로 생성
     */
    @PostMapping("/customer-key")
    public ResponseEntity<?> getOrMakeCustomerKey() {
        Long memberId = 1L; // TODO: 시큐리티 적용
        CustomerKeyResponse result = billingService.getOrMakeCustomerKey(memberId);
        return ResponseEntity.ok(new ApiResponse<>("BI100", "키 조회/생성 완료", result));
    }

    /**
     * 활성화된 결제 카드 존재 여부 조회 API
     * - 회원이 등록한 카드 중 is_active = 'Y' 상태가 존재하는지 확인
     * - 존재하면 true, 없으면 false 반환
     */
    @GetMapping("/card/status")
    public ResponseEntity<?> hasActiveCard() {
        Long memberId = 1L; // TODO: 시큐리티 적용
        CardStatusResponse result = billingService.hasActiveCard(memberId);
        return ResponseEntity.ok(new ApiResponse<>("BI100", "활성화 된 카드 조회 성공", result));
    }

}
