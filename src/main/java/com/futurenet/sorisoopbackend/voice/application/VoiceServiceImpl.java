package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.voice.application.exception.VoiceErrorCode;
import com.futurenet.sorisoopbackend.voice.application.exception.VoiceException;
import com.futurenet.sorisoopbackend.voice.domain.VoiceRepository;
import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.request.UpdateVoiceInfoRequest;
import com.futurenet.sorisoopbackend.voice.dto.response.DeleteVoiceResponse;
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
    private final AmazonS3Service amazonS3Service;

    @Transactional
    @Override
    public List<GetVoiceResponse> getVoiceList(Long memberId) {
        List<GetVoiceResponse> voices = voiceRepository.getVoiceList(memberId);
        return voices != null ? voices : Collections.emptyList();
    }

    @Transactional
    @Override
    public void addVoice(AddVoiceRequest request, String voiceUrl, Long memberId) {
        request.setTtsUrl(voiceUrl);
        int result = voiceRepository.saveVoice(request, memberId);

        if (result == 0) {
            throw new VoiceException(VoiceErrorCode.VOICE_SAVE_FAIL);
        }
    }

    @Transactional
    @Override
    public void updateVoiceInfo(Long voiceId, UpdateVoiceInfoRequest request, Long memberId) {
        try {
            int result = voiceRepository.updateVoiceInfo(voiceId, request, memberId);
            if (result == 0) {
                throw new VoiceException(VoiceErrorCode.VOICE_NOT_FOUND);
            }
        }  catch (Exception e) {
            throw new VoiceException(VoiceErrorCode.VOICE_UPDATE_FAIL);
        }
    }

    @Transactional
    @Override
    public void delectVoice(Long voiceId) {

        DeleteVoiceResponse response = voiceRepository.getVoiceForDelete(voiceId);
        if(response == null){
            throw new VoiceException(VoiceErrorCode.VOICE_NOT_FOUND);
        }

        try{
            amazonS3Service.deleteFile(response.getTtsUrl());
        }
        catch (Exception e){
            throw new VoiceException(VoiceErrorCode.S3_FILE_DELECT_FAIL);
        }

        int result = voiceRepository.deleteVoice(voiceId);
        if(result == 0){
            throw new VoiceException(VoiceErrorCode.VOICE_DELETE_FAIL);
        }
    }
}
