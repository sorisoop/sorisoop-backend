package com.futurenet.sorisoopbackend.fairytale.presentation;

import com.futurenet.sorisoopbackend.fairytale.application.FairyTaleService;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FIndFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fairy-tale")
@RequiredArgsConstructor
public class FairyTaleController {

    private final FairyTaleService fairyTaleService;

    @GetMapping("/{fairyTaleId}/contents")
    public ResponseEntity<?> readFairyTale(@PathVariable Long fairyTaleId) {
        List<FindFairyTaleContentResponse> result = fairyTaleService.getFairyTaleContents(fairyTaleId);
        return ResponseEntity.ok(new ApiResponse<>("FT100", "동화 내용 조회 성공", result));
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        List<FairyTaleCategoryResponse> result = fairyTaleService.getFairyTaleCategories();
        return ResponseEntity.ok(new ApiResponse<>("FT101", "동화 카테고리 조회 성공", result));
    }
}
