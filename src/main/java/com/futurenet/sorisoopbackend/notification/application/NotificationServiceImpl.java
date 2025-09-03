package com.futurenet.sorisoopbackend.notification.application;

import com.futurenet.sorisoopbackend.notification.application.exception.NotificationErrorCode;
import com.futurenet.sorisoopbackend.notification.application.exception.NotificationException;
import com.futurenet.sorisoopbackend.notification.domain.NotificationRepository;
import com.futurenet.sorisoopbackend.notification.domain.NotificationStatusRepository;
import com.futurenet.sorisoopbackend.notification.dto.response.GetNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationStatusRepository notificationStatusRepository;
    private final NotificationRepository notificationRepository;
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter subscribe(Long profileId) {
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L);
        emitters.put(profileId, emitter);

        emitter.onCompletion(() -> emitters.remove(profileId));
        emitter.onTimeout(() -> emitters.remove(profileId));
        emitter.onError(e -> emitters.remove(profileId));

        try {
            emitter.send(SseEmitter.event().name("CONNECTED").data("SSE 연결 성공"));
        } catch (IOException e) {
            emitters.remove(profileId);
        }

        return emitter;
    }

    public void sendToUser(Long profileId, String message) {
        SseEmitter emitter = emitters.get(profileId);
        if (emitter == null) {
            System.out.println("알림 없음요: " + profileId);
            return;
        }


        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("make-fairy-tale-complete")
                        .data(message));
            } catch (IOException e) {
                emitters.remove(profileId);
            }
        }
    }

    @Override
    public List<GetNotificationResponse> getAllNotifications(Long profileId) {
        return notificationRepository.getAllNotifications(profileId);
    }

    @Override
    public boolean checkUnreadNotifications(Long profileId) {
        return notificationRepository.checkUnreadNotifications(profileId);
    }

    @Override
    @Transactional
    public void readNotification(Long profileId, Long notificationId) {
        int result = notificationRepository.readNotification(profileId, notificationId);

        if (result == 0) {
            throw new NotificationException(NotificationErrorCode.READ_NOTIFICATION_FAIL);
        }

    }

    @Override
    @Transactional
    public void saveNotification(Long profileId, Long customFairyTaleId, String content) {
        notificationRepository.saveNotification(profileId, customFairyTaleId, content);
    }

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
