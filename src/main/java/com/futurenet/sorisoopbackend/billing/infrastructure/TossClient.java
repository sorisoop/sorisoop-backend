package com.futurenet.sorisoopbackend.billing.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.billing.application.exception.BillingErrorCode;
import com.futurenet.sorisoopbackend.billing.application.exception.BillingException;
import com.futurenet.sorisoopbackend.billing.dto.response.BrandPayCardResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.CustomerTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

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
            throw new BillingException(BillingErrorCode.BILLING_KEY_ISSUE_FAIL);
        } catch (Exception e) {
            throw new BillingException(BillingErrorCode.BILLING_UNKNOWN_ERROR);
        }
    }

    public List<BrandPayCardResponse> getPaymentMethods(String customerKey, String customerToken) {
        String url = TOSS_BASE_URL + "/brandpay/payments/methods?customerKey=" + customerKey;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + customerToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            JsonNode body = objectMapper.readTree(response.getBody());
            List<BrandPayCardResponse> cards = new ArrayList<>();

            if (body.has("cards")) {
                for (JsonNode card : body.get("cards")) {
                    cards.add(BrandPayCardResponse.of(card));
                }
            }

            return cards;
        } catch (Exception e) {
            throw new BillingException(BillingErrorCode.BILLING_UNKNOWN_ERROR);
        }
    }

    public void deletePaymentMethod(String customerToken, String methodKey) {
        String url = TOSS_BASE_URL + "/brandpay/payments/methods/card/remove";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(customerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("methodKey", methodKey);
        log.info("Deleting card with token={}, methodKey={}", customerToken, methodKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        log.info("Toss delete request headers={}, body={}", entity.getHeaders(), entity.getBody());

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            log.info("Delete response: {}", response.getBody());
        } catch (HttpStatusCodeException e) {
            log.error("Failed to delete payment method: {}", e.getResponseBodyAsString());
            throw new BillingException(BillingErrorCode.BILLING_CARD_DELETE_FAIL);
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

            log.info("결제 승인 성공: {}", responseBody);
            return objectMapper.readTree(responseBody);
        } catch (HttpStatusCodeException e) {
            log.error("결제 승인 실패: {}", e.getResponseBodyAsString());
            throw new BillingException(BillingErrorCode.PAYMENT_CONFIRM_FAIL);
        } catch (Exception e) {
            log.error("결제 승인 중 알 수 없는 오류", e);
            throw new BillingException(BillingErrorCode.BILLING_UNKNOWN_ERROR);
        }
    }

}
