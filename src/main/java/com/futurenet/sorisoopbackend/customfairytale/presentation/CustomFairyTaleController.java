package com.futurenet.sorisoopbackend.customfairytale.presentation;

import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResponse;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> makeCustomFairyTale(@RequestParam("image") MultipartFile image) {
        List<MakeCustomFairyTaleResponse> result = makeFairyTaleService.createCustomFairyTale(image);
        return ResponseEntity.ok(new ApiResponse<>("CF100", "커스텀 동화 생성 완료", result));
    }
}
