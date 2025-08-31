package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.request.UpdateVoiceInfoRequest;
import com.futurenet.sorisoopbackend.voice.dto.response.GetVoiceResponse;

import java.util.List;

public interface VoiceService {
    List<GetVoiceResponse> getVoiceList(Long memberId);
    void addVoice(AddVoiceRequest request, String voiceUrl, Long memberId);
    void updateVoiceInfo(Long voiceId, UpdateVoiceInfoRequest request, Long memberId);
    void delectVoice(Long voiceId);
}
