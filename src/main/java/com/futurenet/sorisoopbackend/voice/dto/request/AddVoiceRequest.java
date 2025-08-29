package com.futurenet.sorisoopbackend.voice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddVoiceRequest {
    private String title;
    private String imageUrl;
    @Setter
    private String ttsUrl;
}
