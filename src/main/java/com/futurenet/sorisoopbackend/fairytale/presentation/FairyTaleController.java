package com.futurenet.sorisoopbackend.fairytale.presentation;

import com.futurenet.sorisoopbackend.fairytale.application.FairyTaleService;
import com.futurenet.sorisoopbackend.fairytale.application.FavoriteService;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fairy-tale")
@RequiredArgsConstructor
public class FairyTaleController {

    private final FairyTaleService fairyTaleService;
    private final FavoriteService favoriteService;

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

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> getFairyTaleList(@PathVariable Long categoryId, @RequestParam int page) {
        List<FairyTaleResponse> result = fairyTaleService.getFairyTaleList(categoryId, page);
        return ResponseEntity.ok(new ApiResponse<>("FT102", "카테고리 별 동화책 목록 조회 성공", result));
    }

    @GetMapping
    public ResponseEntity<?> searchFairyTales(@RequestParam String keyword, @RequestParam int page) {
        List<FairyTaleResponse> result = fairyTaleService.searchFairyTaleList(keyword, page);
        return ResponseEntity.ok(new ApiResponse<>("FT103", "동화책 검색 성공", result));
    }

    @GetMapping("/{fairyTaleId}")
    public ResponseEntity<?> getFairyTaleDetail(@PathVariable Long fairyTaleId){
        FairyTaleResponse result = fairyTaleService.getFairyTaleDetail(fairyTaleId);
        return ResponseEntity.ok(new ApiResponse<>("FT104", "동화책 상세 조회 성공", result));
    }

    @PostMapping("/{fairyTaleId}/favorites")
    public ResponseEntity<?> addFavorite(@PathVariable Long fairyTaleId, @RequestParam Long profileId) {
        favoriteService.addFavorite(fairyTaleId, profileId);
        return ResponseEntity.ok(new ApiResponse<>("FT201", "동화책 찜하기 성공", null));
    }

    @GetMapping("/{profileId}/favorites")
    public ResponseEntity<?> getFavorites(@PathVariable Long profileId, @RequestParam int page) {
        List<FairyTaleResponse> result = favoriteService.getFavoriteFairyTales(profileId, page);
        return ResponseEntity.ok(new ApiResponse<>("FT202", "찜한 동화책 목록 조회 성공", result));
    }

    @DeleteMapping("/{fairyTaleId}/favorites")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long fairyTaleId, @RequestParam Long profileId) {
        favoriteService.deleteFavorite(fairyTaleId, profileId);
        return ResponseEntity.ok(new ApiResponse<>("FT203", "찜한 동화책 삭제 성공", null));
    }
}
