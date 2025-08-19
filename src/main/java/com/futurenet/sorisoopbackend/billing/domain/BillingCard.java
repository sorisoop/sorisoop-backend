package com.futurenet.sorisoopbackend.billing.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillingCard {
    private Long id;
    private Long memberId;
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
    private String isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
