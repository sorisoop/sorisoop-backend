package com.futurenet.sorisoopbackend.log.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.log.application.ReadLogService;
import com.futurenet.sorisoopbackend.log.dto.request.SaveReadLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/read-logs")
@RequiredArgsConstructor
public class ReadLogController {

    private final ReadLogService readLogService;

    @PostMapping
    public ResponseEntity<?> saveReadLog(@RequestBody SaveReadLogRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        readLogService.saveReadLog(request, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("RL100", "읽기 기록 저장 성공", null));
    }
}
