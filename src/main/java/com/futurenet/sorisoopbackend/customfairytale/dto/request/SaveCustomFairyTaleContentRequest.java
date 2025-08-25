package com.futurenet.sorisoopbackend.customfairytale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveCustomFairyTaleContentRequest {
    private Long customFairyTaleId;
    private int page;
    private String imageUrl;
    private String script;
}
