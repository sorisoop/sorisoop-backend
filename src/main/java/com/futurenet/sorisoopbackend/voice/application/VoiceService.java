package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.response.GetVoiceResponse;

import java.util.List;

public interface VoiceService {
    List<GetVoiceResponse> getVoiceList(Long profileId);
    void addVoice(AddVoiceRequest request, String voiceUrl);
}
