package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.voice.application.exception.VoiceErrorCode;
import com.futurenet.sorisoopbackend.voice.application.exception.VoiceException;
import com.futurenet.sorisoopbackend.voice.domain.VoiceRepository;
import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.request.UpdateVoiceInfoRequest;
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
        try {
            request.setTtsUrl(voiceUrl);
            int inserted = voiceRepository.saveVoice(request);
            if (inserted == 0) {
                throw new VoiceException(VoiceErrorCode.VOICE_SAVE_FAIL);
            }
        } catch (Exception e) {
            throw new VoiceException(VoiceErrorCode.S3_FILE_UPLOAD_FAIL);
        }
    }

    @Transactional
    @Override
    public void updateVoiceInfo(Long voiceId, UpdateVoiceInfoRequest request) {
        try {
            int updated = voiceRepository.updateVoiceInfo(voiceId, request);
            if (updated == 0) {
                throw new VoiceException(VoiceErrorCode.VOICE_NOT_FOUND);
            }
        }  catch (Exception e) {
            throw new VoiceException(VoiceErrorCode.VOICE_UPDATE_FAIL);
        }
    }

}
