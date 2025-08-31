package com.futurenet.sorisoopbackend.voice.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.voice.application.VoiceService;
import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.request.UpdateVoiceInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voices")
public class VoiceController {

    private final VoiceService voiceService;
    private final AmazonS3Service amazonS3Service;

    @GetMapping
    public ResponseEntity<?> getVoices(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(new ApiResponse<>("VO100", "목소리 리스트 조회 완료", voiceService.getVoiceList(userPrincipal.getId())));
    }

    @PostMapping
    public ResponseEntity<?> addVoice(@RequestPart("voice") MultipartFile voiceFile, @RequestParam("request") String requestJson, @AuthenticationPrincipal UserPrincipal userPrincipal) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AddVoiceRequest request = mapper.readValue(requestJson, AddVoiceRequest.class);

        String voiceUrl = amazonS3Service.uploadAudio(voiceFile, FolderNameConstant.VOICE);
        voiceService.addVoice(request, voiceUrl, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("VO101", "목소리 등록 완료", null));
    }

    @PatchMapping("/{voiceId}")
    public ResponseEntity<?> updateVoiceInfo(@PathVariable Long voiceId, @RequestBody UpdateVoiceInfoRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal){
        voiceService.updateVoiceInfo(voiceId, request, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("VO102", "목소리 정보 수정 완료", null));
    }

    @DeleteMapping("/{voiceId}")
    public ResponseEntity<?> delectVoice(@PathVariable Long voiceId) {
        voiceService.delectVoice(voiceId);
        return ResponseEntity.ok(new ApiResponse<>("VO103", "목소리 삭제 완료", null));
    }
}
