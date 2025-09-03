package com.futurenet.sorisoopbackend.notification.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.notification.application.NotificationService;
import com.futurenet.sorisoopbackend.notification.dto.response.GetNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

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

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return notificationService.subscribe(userPrincipal.getProfileId());
    }

    @GetMapping
    public ResponseEntity<?> getNotifications(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<GetNotificationResponse> result = notificationService.getAllNotifications(userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("NF102", "알림 전체 조회 성공", result));
    }

    @GetMapping("/unread")
    public ResponseEntity<?> checkUnreadNotifications(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        boolean result = notificationService.checkUnreadNotifications(userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("NF103", "안읽은 알림 여부 조회 성공", result));
    }

    @PatchMapping("/{notificationId}")
    public ResponseEntity<?> readNotification(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long notificationId) {
        notificationService.readNotification(userPrincipal.getProfileId(), notificationId);
        return ResponseEntity.ok(new ApiResponse<>("NF104", "알림 읽기 성공", null));
    }

}
