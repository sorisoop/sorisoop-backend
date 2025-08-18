package com.futurenet.sorisoopbackend.voice.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.voice.application.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voices")
public class VoiceController {
    private final VoiceService voiceService;


    @GetMapping
    public ResponseEntity<?> getVoices(@RequestParam("profileId") Long profileId) {
        return ResponseEntity.ok(new ApiResponse<>("VO", "목소리 리스트 조회 완료", voiceService.getVoiceList(profileId)));
    }

}
