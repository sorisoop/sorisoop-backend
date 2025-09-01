package com.futurenet.sorisoopbackend.notification.application;

public interface NotificationService {
    String getNotificationStatus(Long profileId);
    void updateNotificationStatus(Long profileId);
}
