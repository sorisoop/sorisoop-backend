package com.futurenet.sorisoopbackend.customfairytale.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.service.CustomFairyTaleProducer;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/fairy-tale/custom")
@RequiredArgsConstructor
public class CustomFairyTaleController {

    private final MakeFairyTaleService makeFairyTaleService;
    private final CustomFairyTaleProducer customFairyTaleProducer;
    private final AmazonS3Service amazonS3Service;

    @PostMapping("/synopsis")
    public ResponseEntity<?> makeCustomFairyTaleSynopsis(@RequestParam("image") MultipartFile image, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<String> result = makeFairyTaleService.makeSynopsis(image, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("CF100", "동화 시놉시스 생성 완료", result));
    }


    @PostMapping
    public ResponseEntity<?> makeCustomFairyTale(@RequestParam("image") MultipartFile image, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        String imageUrl = amazonS3Service.uploadImage(image, FolderNameConstant.USER_DRAWING);
        customFairyTaleProducer.sendMakeFairyTaleRequest(new MakeCustomFairyTaleRequest(userPrincipal.getProfileId(), imageUrl, image.getContentType()));
        return ResponseEntity.ok(new ApiResponse<>("CF101", "커스텀 동화 생성 완료", null));
    }
}
