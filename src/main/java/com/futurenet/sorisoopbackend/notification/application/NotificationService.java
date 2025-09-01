package com.futurenet.sorisoopbackend.notification.application;

import com.futurenet.sorisoopbackend.notification.dto.response.GetNotificationResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationService {
    SseEmitter subscribe(Long profileId);
    String getNotificationStatus(Long profileId);
    void updateNotificationStatus(Long profileId);
    void sendToUser(Long profileId, String message);
    List<GetNotificationResponse> getAllNotifications(Long profileId);
    boolean checkUnreadNotifications(Long profileId);
    void readNotification(Long profileId, Long notificationId);
    void saveNotification(Long profileId, Long notificationId, String content);
}
