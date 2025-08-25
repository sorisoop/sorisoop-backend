package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.global.infrastructure.config.RabbitConfig;
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

    @RabbitListener(queues = RabbitConfig.FAIRYTALE_QUEUE)
    public void receiveMakeFairyTaleRequest(MakeCustomFairyTaleRequest request) {
        try {
            List<MakeCustomFairyTaleContentResponse> response = makeFairyTaleService.createCustomFairyTale(request);
            log.info("결과: {}:", response.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
