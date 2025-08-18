package com.futurenet.sorisoopbackend.billing.infrastructure;

import com.futurenet.sorisoopbackend.billing.application.exception.BillingErrorCode;
import com.futurenet.sorisoopbackend.billing.application.exception.BillingException;
import com.futurenet.sorisoopbackend.billing.dto.response.BillingKeyResponse;
import com.futurenet.sorisoopbackend.billing.dto.response.PaymentMethodsResponse;
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

    @Value("${billing.secret-key}")
    private String secretKey;
    private static final String TOSS_BASE_URL = "https://api.tosspayments.com/v1";

    /**
     * 빌링키 발급 요청 -> 카드정보 대신 사용하는 키
     */
    public BillingKeyResponse issueBillingKey(String customerKey, String authKey) {
        String url = TOSS_BASE_URL + "/billing/authorizations/issue";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String encodedAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        Map<String, String> body = new HashMap<>();
        body.put("customerKey", customerKey);
        body.put("authKey", authKey);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<BillingKeyResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    BillingKeyResponse.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new BillingException(BillingErrorCode.BILLING_KEY_ISSUE_FAIL);
        } catch (Exception e) {
            throw new BillingException(BillingErrorCode.BILLING_UNKNOWN_ERROR);
        }
    }
}
