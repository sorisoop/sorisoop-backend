package com.futurenet.sorisoopbackend.subscription.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.brandpaytoken.application.exception.BrandPayTokenErrorCode;
import com.futurenet.sorisoopbackend.brandpaytoken.application.exception.BrandPayTokenException;
import com.futurenet.sorisoopbackend.brandpaytoken.dto.response.CustomerTokenResponse;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubscriptionErrorCode;
import com.futurenet.sorisoopbackend.subscription.application.exception.SubscriptionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    @Value("${billing.secret-key}")
    private String secretKey;
    private static final String TOSS_BASE_URL = "https://api.tosspayments.com/v1";

    public CustomerTokenResponse issueCustomerToken(String customerKey, String code) {
        String url = TOSS_BASE_URL + "/brandpay/authorizations/access-token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String encodedAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        Map<String, String> body = new HashMap<>();
        body.put("grantType", "AuthorizationCode");
        body.put("customerKey", customerKey);
        body.put("code", code);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<CustomerTokenResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    CustomerTokenResponse.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            log.error("Failed to issue customerToken: {}", e.getResponseBodyAsString());
            throw new BrandPayTokenException(BrandPayTokenErrorCode.BRANDPAY_TOKEN_ISSUE_FAIL);
        } catch (Exception e) {
            throw new SubscriptionException(SubscriptionErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 브랜드페이 결제 승인
     * @param paymentKey 프론트 successUrl에서 전달받은 paymentKey
     * @param orderId 프론트 successUrl에서 전달받은 orderId
     * @param amount 프론트 successUrl에서 전달받은 amount
     * @return 결제 승인 결과 JSON
     */
    public JsonNode confirmPayment(String paymentKey, String orderId, int amount) {
        String url = TOSS_BASE_URL + "/payments/confirm";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String encodedAuth = Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", paymentKey);
        body.put("orderId", orderId);
        body.put("amount", amount);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            String responseBody = new String(
                    response.getBody().getBytes(StandardCharsets.ISO_8859_1),
                    StandardCharsets.UTF_8
            );

            return objectMapper.readTree(responseBody);
        } catch (HttpStatusCodeException e) {
            log.error("결제 승인 실패: {}", e.getResponseBodyAsString());
            throw new SubscriptionException(SubscriptionErrorCode.PAYMENT_CONFIRM_FAIL);
        } catch (Exception e) {
            log.error("결제 승인 중 알 수 없는 오류", e);
            throw new SubscriptionException(SubscriptionErrorCode.UNKNOWN_ERROR);
        }
    }
}

