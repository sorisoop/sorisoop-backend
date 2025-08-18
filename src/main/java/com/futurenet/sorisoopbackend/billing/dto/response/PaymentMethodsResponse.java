package com.futurenet.sorisoopbackend.billing.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaymentMethodsResponse {
    private List<BrandPayMethod> cards;
    private boolean isIdentified;
    private String selectedMethodId;

    @Data
    public static class BrandPayMethod {
        private String id;
        private String methodKey;
        private String cardName;
        private String cardNumber;
        private String issuerCode;
        private String cardType;
        private String status;
        private String iconUrl;
        private String registeredAt;
    }
}

