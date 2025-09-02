package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.domain.NotificationContent;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResult;
import com.futurenet.sorisoopbackend.global.infrastructure.config.RabbitConfig;
import com.futurenet.sorisoopbackend.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomFairyTaleConsumer {

    private final MakeFairyTaleService makeFairyTaleService;
    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitConfig.FAIRYTALE_QUEUE)
    public void receiveMakeFairyTaleRequest(MakeCustomFairyTaleRequest request) {
        try {
            MakeCustomFairyTaleResult result = makeFairyTaleService.createCustomFairyTale(request);
            log.info("결과: {}:", result.getPages().toString());
            // 알림 전송 및 저장
            notificationService.saveNotification(request.getProfileId(), result.getCustomFairyTaleId(), NotificationContent.MAKE_FAIRY_TALE_COMPLETE.getMessage());

            if (notificationService.getNotificationStatus(request.getProfileId()).equals("T")) {
                notificationService.sendToUser(request.getProfileId(), "동화 생성을 완료했습니다.");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
