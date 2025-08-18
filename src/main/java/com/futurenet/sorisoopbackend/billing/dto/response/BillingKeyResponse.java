package com.futurenet.sorisoopbackend.billing.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingKeyResponse {
    private String billingKey;
    private String customerKey;
    private CreditCard card;

    @Data
    public static class CreditCard {
        private String number;
        private String issuerCode;
    }
}
