package com.futurenet.sorisoopbackend.tts.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCustomTtsRequest {
    private String voiceUuid;
    private Long customFairyTaleId;
}
