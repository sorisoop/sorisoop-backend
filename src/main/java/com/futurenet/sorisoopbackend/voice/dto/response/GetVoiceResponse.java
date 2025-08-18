package com.futurenet.sorisoopbackend.voice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetVoiceResponse {
    private Long id;
    private Long profileId;
    private String imageUrl;
    private String title;
}
