package com.futurenet.sorisoopbackend.fairytale.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.fairytale.application.FairyTaleService;
import com.futurenet.sorisoopbackend.fairytale.application.FavoriteService;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleResponse;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        List<FindFairyTaleCategoryResponse> result = fairyTaleService.getFairyTaleCategories();
        return ResponseEntity.ok(new ApiResponse<>("FT101", "동화 카테고리 조회 성공", result));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> getFairyTaleList(@PathVariable Long categoryId, @RequestParam int page, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FindFairyTaleResponse> result = fairyTaleService.getFairyTaleList(categoryId, page, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("FT102", "카테고리 별 동화책 목록 조회 성공", result));
    }

    @GetMapping
    public ResponseEntity<?> searchFairyTales(@RequestParam String keyword, @RequestParam int page, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FindFairyTaleResponse> result = fairyTaleService.searchFairyTaleList(keyword, page, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("FT103", "동화책 검색 성공", result));
    }

    @GetMapping("/{fairyTaleId}")
    public ResponseEntity<?> getFairyTaleDetail(@PathVariable Long fairyTaleId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        FindFairyTaleResponse result = fairyTaleService.getFairyTaleDetail(fairyTaleId, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("FT104", "동화책 상세 조회 성공", result));
    }

    @GetMapping("/random")
    public ResponseEntity<?> getFairyTalesRandom(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FindFairyTaleResponse> result = fairyTaleService.getFairyTalesRandom(userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("FT105", "동화책 랜덤 조회", result));
    }

    @PostMapping("/{fairyTaleId}/favorites")
    public ResponseEntity<?> addFavorite(@PathVariable Long fairyTaleId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        favoriteService.addFavorite(fairyTaleId, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("FT201", "동화책 찜하기 성공", null));
    }

    @GetMapping("/favorites")
    public ResponseEntity<?> getFavorites(@RequestParam int page, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FindFairyTaleResponse> result = favoriteService.getFavoriteFairyTales(userPrincipal.getProfileId(), page);
        return ResponseEntity.ok(new ApiResponse<>("FT202", "찜한 동화책 목록 조회 성공", result));
    }

    @DeleteMapping("/{fairyTaleId}/favorites")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long fairyTaleId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        favoriteService.deleteFavorite(fairyTaleId, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("FT203", "찜한 동화책 삭제 성공", null));
    }
}
