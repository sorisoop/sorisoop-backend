package com.futurenet.sorisoopbackend.billing.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandPayCardResponse {
    private String methodKey;
    private String cardName;
    private String cardNumber;
    private String acquirerCode;
    private String cardType;
    private String status;
    private String iconUrl;
    private String cardImgUrl;
    private String colorBg;
    private String colorText;

    public static BrandPayCardResponse of(JsonNode card) {
        return BrandPayCardResponse.builder()
                .methodKey(card.path("methodKey").asText())
                .cardName(card.path("cardName").asText())
                .cardNumber(card.path("cardNumber").asText())
                .acquirerCode(card.path("acquirerCode").asText())
                .cardType(card.path("cardType").asText())
                .status(card.path("status").asText())
                .iconUrl(card.path("iconUrl").asText())
                .cardImgUrl(card.path("cardImgUrl").asText())
                .colorBg(card.path("color").path("background").asText())
                .colorText(card.path("color").path("text").asText())
                .build();
    }
}
