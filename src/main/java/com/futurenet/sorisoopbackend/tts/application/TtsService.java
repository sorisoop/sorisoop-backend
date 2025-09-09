package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;

public interface TtsService {
    GetTtsResponse createTts(GetTtsRequest request, Long profileId);
//    GetTtsResponse getTts(String voiceId, Long fairyTaleId, int page, Long profileId);
//    GetTtsResponse createCustomTts(GetCustomTtsRequest request, Long profileId);
}
