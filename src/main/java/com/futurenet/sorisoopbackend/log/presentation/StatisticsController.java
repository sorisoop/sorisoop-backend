package com.futurenet.sorisoopbackend.log.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.log.application.StatisticsService;
import com.futurenet.sorisoopbackend.log.dto.response.GetCategoryStatisticsResponse;
import com.futurenet.sorisoopbackend.log.dto.response.GetCompletionStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/{childProfileId}/categories")
    public ResponseEntity<?> getCategoryStatistics(@PathVariable Long childProfileId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<GetCategoryStatisticsResponse> result = statisticsService.getCategoryStatistics(childProfileId, userPrincipal.getProfileId(), userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("ST100", "카테고리 통계 조회 성공", result));
    }

    @GetMapping("/{childProfileId}/completion")
    public ResponseEntity<?> getCompletionStatistics(@PathVariable Long childProfileId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<GetCompletionStatisticsResponse> result = statisticsService.getCompletionStatistics(childProfileId, userPrincipal.getProfileId(), userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("ST101", "완독 비율 통계 조회 성공", result));
    }

    @GetMapping("/{childProfileId}/")

}
