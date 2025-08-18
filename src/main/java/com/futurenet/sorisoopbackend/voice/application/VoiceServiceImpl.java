package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.voice.domain.VoiceRepository;
import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.response.GetVoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoiceServiceImpl implements VoiceService{

    private final VoiceRepository voiceRepository;

    @Transactional
    @Override
    public List<GetVoiceResponse> getVoiceList(Long profileId) {
        List<GetVoiceResponse> voices = voiceRepository.getVoiceList(profileId);
        return voices != null ? voices : Collections.emptyList();
    }

    @Transactional
    @Override
    public void addVoice(AddVoiceRequest request, String voiceUrl) {
        request.setTtsUrl(voiceUrl);
        voiceRepository.saveVoice(request);
    }

}
