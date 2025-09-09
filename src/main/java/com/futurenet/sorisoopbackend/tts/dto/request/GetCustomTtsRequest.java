package com.futurenet.sorisoopbackend.tts.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCustomTtsRequest {
    private String speakerId;
    private Long customFairyTaleId;
}
