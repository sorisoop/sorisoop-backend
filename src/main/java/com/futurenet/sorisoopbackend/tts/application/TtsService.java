package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.dto.request.GetCustomTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;
import com.futurenet.sorisoopbackend.tts.dto.response.GetVoiceUuidResponse;

public interface TtsService {
    GetVoiceUuidResponse addSpeakers(Long voiceId, Long profileId);
    GetTtsResponse createTts(GetTtsRequest request, Long profileId);
    GetTtsResponse getTts(String voiceUuid, int page, Long profileId);
    GetTtsResponse createCustomTts(GetCustomTtsRequest request, Long profileId);
}
