package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.dto.response.GetVoiceUuidResponse;

public interface TtsService {
    GetVoiceUuidResponse addSpeakers(Long voiceId);
}
