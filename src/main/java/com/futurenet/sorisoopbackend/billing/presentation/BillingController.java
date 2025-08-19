package com.futurenet.sorisoopbackend.billing.presentation;

import com.futurenet.sorisoopbackend.billing.application.BillingService;
import com.futurenet.sorisoopbackend.billing.dto.response.CardStatusResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CreditCardResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerKeyResponse;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    // ────────────────────────────────
    // CustomerKey 관련
    // ────────────────────────────────

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

    // ────────────────────────────────
    // 카드 관련
    // ────────────────────────────────

    /**
     * Brandpay Redirect Callback (토큰 저장)
     * - 토스가 리다이렉트 시켜줄 때 customerKey + code를 쿼리스트링으로 전달
     * - 여기서 customerToken을 발급받아 저장
     */
    @GetMapping("/callback-auth")
    public ResponseEntity<?> callBackAuth(@RequestParam String customerKey,
                                     @RequestParam String code) {
        Long memberId = 1L; // TODO: 시큐리티 적용
        billingService.handleBrandpayAuth(memberId, customerKey, code);
        return ResponseEntity.ok(new ApiResponse<>("BI100", "브랜드페이 인증 성공", null));
    }

    /**
     * 카드 조회 API
     */
    @GetMapping("/card")
    public ResponseEntity<?> getCards() {
        Long memberId = 1L; // TODO: 시큐리티 적용
        List<CreditCardResponse> result = billingService.getCards(memberId);
        return ResponseEntity.ok(new ApiResponse<>("BI100", "카드 조회 성공", result));
    }

    /**
     * 카드 직접 등록 API
     *
     */
    @PostMapping("/card")
    public ResponseEntity<?> registerCard() {
        Long memberId = 1L; // TODO: 시큐리티 적용
        billingService.registerCard(memberId);
        return ResponseEntity.ok(new ApiResponse<>("BI100", "카드 등록 성공", null));
    }

    /**
     * 카드 삭제 API
     */
    @DeleteMapping("/card")
    public ResponseEntity<?> deleteCard(@RequestParam Long cardId) {
        Long memberId = 1L; // TODO: 시큐리티 적용
        billingService.deleteCard(memberId, cardId);
        return ResponseEntity.ok(new ApiResponse<>("BI100", "카드 삭제 성공", null));
    }

    /**
     * 활성화된 결제 카드 존재 여부 조회 API
     * - 회원이 등록한 카드 중 is_active = 'Y' 상태가 존재하는지 확인
     */
    @GetMapping("/card/status")
    public ResponseEntity<?> hasActiveCard() {
        Long memberId = 1L; // TODO: 시큐리티 적용
        CardStatusResponse result = billingService.hasActiveCard(memberId);
        return ResponseEntity.ok(new ApiResponse<>("BI100", "활성화 된 카드 조회 성공", result));
    }
}
