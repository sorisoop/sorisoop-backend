package com.futurenet.sorisoopbackend.notification.domain;

import com.futurenet.sorisoopbackend.notification.dto.response.GetNotificationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationRepository {
    List<GetNotificationResponse> getAllNotifications(Long profileId);
    boolean checkUnreadNotifications(Long profileId);
    int readNotification(Long profileId, Long notificationId);
    void saveNotification(Long profileId, Long customFairyTaleId, String content);
}
