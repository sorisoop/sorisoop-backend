package com.futurenet.sorisoopbackend.voice.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.voice.application.VoiceService;
import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voices")
public class VoiceController {

    private final VoiceService voiceService;
    private final AmazonS3Service amazonS3Service;

    @GetMapping
    public ResponseEntity<?> getVoices(@RequestParam("profileId") Long profileId) {
        return ResponseEntity.ok(new ApiResponse<>("VO100", "목소리 리스트 조회 완료", voiceService.getVoiceList(profileId)));
    }

    @PostMapping
    public ResponseEntity<?> addVoice(@RequestPart("voice") MultipartFile voiceFile,
                                      @RequestParam("request") String requestJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        AddVoiceRequest request = mapper.readValue(requestJson, AddVoiceRequest.class);

        String voiceUrl = amazonS3Service.uploadAudio(voiceFile, "voices");
        voiceService.addVoice(request, voiceUrl);
        return ResponseEntity.ok(new ApiResponse<>("VO101", "목소리 등록 완료", null));
    }


}
