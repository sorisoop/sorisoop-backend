package com.futurenet.sorisoopbackend.voice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVoiceInfoRequest {
    private String title;
    private String imageUrl;
}
