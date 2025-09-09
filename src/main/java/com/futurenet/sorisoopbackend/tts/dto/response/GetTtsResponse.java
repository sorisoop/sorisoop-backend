package com.futurenet.sorisoopbackend.tts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetTtsResponse {
    private Long fairyTaleId;
    private Long profileId;
    private String speakerId;
    private List<TtsResult> results;

    @Getter @Setter
    public static class TtsResult {
        private int page;
        private String audio_base64;
    }
}