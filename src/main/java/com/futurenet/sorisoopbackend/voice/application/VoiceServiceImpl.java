package com.futurenet.sorisoopbackend.voice.application;

import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.tts.dto.response.GetSpeakerIdResponse;
import com.futurenet.sorisoopbackend.voice.application.exception.VoiceErrorCode;
import com.futurenet.sorisoopbackend.voice.application.exception.VoiceException;
import com.futurenet.sorisoopbackend.voice.domain.VoiceRepository;
import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.request.UpdateVoiceInfoRequest;
import com.futurenet.sorisoopbackend.voice.dto.response.DeleteVoiceResponse;
import com.futurenet.sorisoopbackend.voice.dto.response.GetVoiceResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class VoiceServiceImpl implements VoiceService{

    private final VoiceRepository voiceRepository;
    private final AmazonS3Service amazonS3Service;
    private final WebClient webClient;

    public VoiceServiceImpl(VoiceRepository voiceRepository, AmazonS3Service amazonS3Service, @Qualifier("ttsWebClient") WebClient webClient) {
        this.voiceRepository = voiceRepository;
        this.amazonS3Service = amazonS3Service;
        this.webClient = webClient;
    }

    @Transactional
    @Override
    public List<GetVoiceResponse> getVoiceList(Long memberId) {
        List<GetVoiceResponse> voices = voiceRepository.getVoiceList(memberId);
        return voices != null ? voices : Collections.emptyList();
    }

    @Transactional
    @Override
    public void addVoice(MultipartFile voiceFile , AddVoiceRequest  request, String voiceUrl, Long memberId) {
        request.setTtsUrl(voiceUrl);

        try {
            byte[] fileBytes = voiceFile.getBytes();

            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("member_id", memberId);
            builder.part("name", request.getTitle());
            builder.part("file", new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return voiceFile.getOriginalFilename();
                }
            });

            GetSpeakerIdResponse response = webClient.post()
                    .uri("/tts/voices")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(GetSpeakerIdResponse.class)
                    .block();

            if (response == null || response.getSpeakerId() == null) {
                throw new VoiceException(VoiceErrorCode.VOICE_SAVE_FAIL);
            }

            String speakerId = response.getSpeakerId();
            int result = voiceRepository.saveVoice(request, memberId, speakerId);

            if (result == 0) {
                throw new VoiceException(VoiceErrorCode.VOICE_SAVE_FAIL);
            }
        } catch (IOException e) {
            throw new VoiceException(VoiceErrorCode.S3_FILE_UPLOAD_FAIL);
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
            throw new VoiceException(VoiceErrorCode.S3_FILE_DELETE_FAIL);
        }

        int result = voiceRepository.deleteVoice(voiceId);
        if(result == 0){
            throw new VoiceException(VoiceErrorCode.VOICE_DELETE_FAIL);
        }
    }
}
