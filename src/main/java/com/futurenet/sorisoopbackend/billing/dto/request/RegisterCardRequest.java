package com.futurenet.sorisoopbackend.billing.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterCardRequest {
    private String customerKey;
    private String authKey;
}