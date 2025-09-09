package com.futurenet.sorisoopbackend.tts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSpeakerIdResponse {
    private Long memberId;
    private String speakerId;
}
