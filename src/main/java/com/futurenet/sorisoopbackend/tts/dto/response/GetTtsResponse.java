package com.futurenet.sorisoopbackend.tts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTtsResponse {
    private int page;
    private byte[] audio_base64;
}