package com.futurenet.sorisoopbackend.tts.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.tts.application.TtsService;
import com.futurenet.sorisoopbackend.tts.dto.response.GetVoiceUuidResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tts")
@RequiredArgsConstructor
public class TtsController {

    private final TtsService ttsService;

    @PostMapping("/speakers/{voiceId}")
    public ResponseEntity<?> addSpeakers(@PathVariable Long voiceId){
        GetVoiceUuidResponse result = ttsService.addSpeakers(voiceId);
        return ResponseEntity.ok(new ApiResponse<>("TS100", "화자 등록 완료", result));
    }
}
