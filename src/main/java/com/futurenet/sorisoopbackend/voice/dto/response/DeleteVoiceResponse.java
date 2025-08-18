package com.futurenet.sorisoopbackend.voice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteVoiceResponse {
    private Long id;
    private String ttsUrl;
}
