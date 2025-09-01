package com.futurenet.sorisoopbackend.notification.application;

import com.futurenet.sorisoopbackend.notification.application.exception.NotificationErrorCode;
import com.futurenet.sorisoopbackend.notification.application.exception.NotificationException;
import com.futurenet.sorisoopbackend.notification.domain.NotificationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationStatusRepository notificationStatusRepository;

    @Override
    public String getNotificationStatus(Long profileId) {
        return notificationStatusRepository.getNotificationStatus(profileId);
    }

    @Override
    @Transactional
    public void updateNotificationStatus(Long profileId) {
        int result = notificationStatusRepository.updateNotificationStatus(profileId);

        if (result == 0) {
            throw new NotificationException(NotificationErrorCode.UPDATE_NOTIFICATION_STATUS_FAIL);
        }
    }
}
