package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.voice.dto.response.VoiceResponse;

import java.util.List;

public interface VoiceService {
    List<VoiceResponse> getVoiceList(Long profileId);
}
