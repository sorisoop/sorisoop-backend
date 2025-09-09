package com.futurenet.sorisoopbackend.tts.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.tts.application.TtsService;
import com.futurenet.sorisoopbackend.tts.dto.request.GetCustomTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tts")
@RequiredArgsConstructor
public class TtsController {

    private final TtsService ttsService;

    @PostMapping
    public ResponseEntity<?> createTts(@RequestBody GetTtsRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ttsService.createTts(request, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("TS101", "일반 동화 TTS 초기 요청 완료", null));
    }

    @GetMapping("/{speakerId}/{fairyTaleId}")
    public ResponseEntity<?> getTts(@PathVariable String speakerId, @PathVariable Long fairyTaleId, @RequestParam int page, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        GetTtsResponse result = ttsService.getTts(speakerId, fairyTaleId, page, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("TS102", "페이지별 일반 동화 TTS 조회 완료", result));
    }

    @PostMapping("/custom")
    public ResponseEntity<?> createCustomTts(@RequestBody GetCustomTtsRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ttsService.createCustomTts(request, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("TS103", "TTS 커스텀 동화 초기 요청 완료", null));
    }

    @GetMapping("/custom/{speakerId}/{customFairyTaleId}")
    public ResponseEntity<?> getCustomTts(@PathVariable String speakerId, @PathVariable Long customFairyTaleId, @RequestParam int page, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        GetTtsResponse result = ttsService.getCustomTts(speakerId, customFairyTaleId, page, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("TS104", "커스텀 동화 페이지별 TTS 조회 완료", result));
    }
}