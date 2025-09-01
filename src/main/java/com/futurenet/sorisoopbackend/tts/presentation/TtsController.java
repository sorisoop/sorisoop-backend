package com.futurenet.sorisoopbackend.tts.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.tts.application.TtsService;
import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;
import com.futurenet.sorisoopbackend.tts.dto.response.GetVoiceUuidResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tts")
@RequiredArgsConstructor
public class TtsController {

    private final TtsService ttsService;

    @PostMapping("/voices/{voiceId}")
    public ResponseEntity<?> addSpeakers(@PathVariable Long voiceId){
        GetVoiceUuidResponse result = ttsService.addSpeakers(voiceId);
        return ResponseEntity.ok(new ApiResponse<>("TS100", "화자 등록 완료", result));
    }

    @PostMapping
    public ResponseEntity<?> createTts(@RequestBody GetTtsRequest request) {
        GetTtsResponse result = ttsService.createTts(request);
        return ResponseEntity.ok(new ApiResponse<>("TS101", "TTS 초기 요청 완료", result));
    }

    @GetMapping("/{voiceUuid}")
    public ResponseEntity<?> getTts(@PathVariable String voiceUuid, @RequestParam int page) {
        GetTtsResponse result = ttsService.getTts(voiceUuid, page);
        return ResponseEntity.ok(new ApiResponse<>("TS102", "페이지별 TTS 조회 완료", result));
    }
}
