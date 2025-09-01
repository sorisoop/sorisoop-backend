package com.futurenet.sorisoopbackend.notification.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/status")
    public ResponseEntity<?> getNotificationStatus(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        String result = notificationService.getNotificationStatus(userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("NF100", "알림 허용 여부 조회 성공", result));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateNotificationStatus(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        notificationService.updateNotificationStatus(userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("NF101", "알림 허용 상태 변경 성공", null));
    }
}
