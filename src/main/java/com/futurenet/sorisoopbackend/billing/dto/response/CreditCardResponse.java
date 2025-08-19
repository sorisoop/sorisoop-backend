package com.futurenet.sorisoopbackend.billing.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurenet.sorisoopbackend.billing.domain.CardCompany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditCardResponse {
    private String id;
    private String cardName;
    private String cardNumber;
    private String acquirerCode;
    private String cardType;
    private String iconUrl;
    private String cardImgUrl;
    private String colorBg;
    private String colorText;
    private String isActive;

    @JsonProperty("cardCompany")
    public String getCardCompany() {
        return CardCompany.fromCode(this.acquirerCode).getKoreanName();
    }
}
