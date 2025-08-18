package com.futurenet.sorisoopbackend.customfairytale.presentation;

import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.service.CustomFairyTaleProducer;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/fairy-tale/custom")
@RequiredArgsConstructor
public class CustomFairyTaleController {

    private final CustomFairyTaleProducer customFairyTaleProducer;
    private final AmazonS3Service amazonS3Service;

    @PostMapping
    public ResponseEntity<?> makeCustomFairyTale(@RequestParam("image") MultipartFile image) {
        String imageUrl = amazonS3Service.uploadImage(image, FolderNameConstant.USER_DRAWING);
        customFairyTaleProducer.sendMakeFairyTaleRequest(new MakeCustomFairyTaleRequest(imageUrl, image.getContentType()));
        return ResponseEntity.ok(new ApiResponse<>("CF100", "커스텀 동화 생성 완료", null));
    }
}
