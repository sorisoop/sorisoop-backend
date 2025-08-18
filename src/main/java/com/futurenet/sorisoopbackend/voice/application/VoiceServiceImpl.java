package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.voice.domain.VoiceMapper;
import com.futurenet.sorisoopbackend.voice.dto.response.VoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoiceServiceImpl implements VoiceService{

    private final VoiceMapper voiceMapper;

    @Transactional
    @Override
    public List<VoiceResponse> getVoiceList(Long profileId) {
        List<VoiceResponse> voices = voiceMapper.getVoiceList(profileId);
        return voices != null ? voices : Collections.emptyList();
    }
}
