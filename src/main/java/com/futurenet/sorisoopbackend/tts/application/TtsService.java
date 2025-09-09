package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.dto.request.GetCustomTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;

public interface TtsService {
    void createTts(GetTtsRequest request, Long profileId);
    GetTtsResponse getTts(String speakerId, Long fairyTaleId, int page, Long profileId);
    void createCustomTts(GetCustomTtsRequest request, Long profileId);
    GetTtsResponse getCustomTts(String speakerId, Long customFairyTaleId, int page, Long profileId);
}
