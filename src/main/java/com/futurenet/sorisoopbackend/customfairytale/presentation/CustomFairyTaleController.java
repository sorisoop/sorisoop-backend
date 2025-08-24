package com.futurenet.sorisoopbackend.customfairytale.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleConceptResponse;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.service.CustomFairyTaleProducer;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/fairy-tale/custom")
@RequiredArgsConstructor
public class CustomFairyTaleController {

    private final MakeFairyTaleService makeFairyTaleService;
    private final CustomFairyTaleProducer customFairyTaleProducer;

    @PostMapping("/synopsis")
    public ResponseEntity<?> makeCustomFairyTaleSynopsis(@RequestParam("image") MultipartFile image, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        MakeCustomFairyTaleConceptResponse result = makeFairyTaleService.makeSynopsis(image, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("CF100", "동화 시놉시스 생성 완료", result));
    }

    @PostMapping
    public ResponseEntity<?> makeCustomFairyTale(@RequestBody MakeCustomFairyTaleRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        request.setProfileId(userPrincipal.getProfileId());
        customFairyTaleProducer.sendMakeFairyTaleRequest(request);
        return ResponseEntity.ok(new ApiResponse<>("CF101", "커스텀 동화 생성 완료", null));
    }
}
