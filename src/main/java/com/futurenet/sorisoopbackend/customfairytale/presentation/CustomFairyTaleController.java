package com.futurenet.sorisoopbackend.customfairytale.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.customfairytale.application.CustomFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.*;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.service.CustomFairyTaleProducer;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/fairy-tale/custom")
@RequiredArgsConstructor
public class CustomFairyTaleController {

    private final MakeFairyTaleService makeFairyTaleService;
    private final CustomFairyTaleProducer customFairyTaleProducer;
    private final CustomFairyTaleService customFairyTaleService;

    @PostMapping("/synopsis")
    public ResponseEntity<?> makeCustomFairyTaleSynopsis(@RequestParam("image") MultipartFile image, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        MakeCustomFairyTaleConceptResponse result = makeFairyTaleService.makeSynopsis(image, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("CF100", "동화 시놉시스 생성 완료", result));
    }

    @PostMapping
    public ResponseEntity<?> makeCustomFairyTale(@RequestBody MakeCustomFairyTaleRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        request.setProfileId(userPrincipal.getProfileId());
        //customFairyTaleProducer.sendMakeFairyTaleRequest(request);
        List<MakeCustomFairyTaleContentResponse> result = makeFairyTaleService.createCustomFairyTale(request);
        return ResponseEntity.ok(new ApiResponse<>("CF101", "커스텀 동화 생성 완료", result));
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomFairyTale(@RequestParam(name = "categoryId") int categoryId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FindCustomFairyTaleResponse> result = customFairyTaleService.getAllCustomFairyTaleByCategoryId(categoryId, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("CF102", "카테고리별 생성 동화 목록 조회 완료", result));
    }

    @GetMapping("/{customFairyTaleId}/contents")
    public ResponseEntity<?> readCustomFairyTale(@PathVariable Long customFairyTaleId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FindCustomFairyTaleContentResponse> result = customFairyTaleService.getCustomFairyTaleContents(customFairyTaleId, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("CF103", "생성 동화 내용 조회 완료", result));
    }

    @GetMapping("/{customFairyTaleId}")
    public ResponseEntity<?> getCustomFairyTaleDetail(@PathVariable Long customFairyTaleId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        FindCustomFairyTaleDetailResponse result = customFairyTaleService.getCustomFairyTaleDetail(customFairyTaleId, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("CF104", "생성 동화 상세 조회 성공", result));
    }
}
