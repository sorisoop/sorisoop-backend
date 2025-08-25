package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.global.infrastructure.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomFairyTaleConsumer {

    private final MakeFairyTaleService makeFairyTaleService;

    @RabbitListener(queues = RabbitConfig.FAIRYTALE_QUEUE)
    public void receiveMakeFairyTaleRequest(MakeCustomFairyTaleRequest request) {
        try {
            System.out.println("컨슈머 동작");
            makeFairyTaleService.createCustomFairyTale(request);
            System.out.println("동화 생성 완료");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("큐 비움");
        }
    }
}
