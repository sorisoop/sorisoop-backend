package com.futurenet.sorisoopbackend.tts.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTtsRequest {
    private String speakerId;
    private Long fairyTaleId;
}
