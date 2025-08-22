package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.application.MakeFairyTaleService;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResponse;
import com.futurenet.sorisoopbackend.global.infrastructure.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomFairyTaleConsumer {

    private final MakeFairyTaleService makeFairyTaleService;

    @RabbitListener(queues = RabbitConfig.FAIRYTALE_QUEUE)
    public void receiveMakeFairyTaleRequest(MakeCustomFairyTaleRequest request) {
        List<MakeCustomFairyTaleResponse> result = makeFairyTaleService.createCustomFairyTale(request);

        for (MakeCustomFairyTaleResponse response : result) {
            System.out.println(response.getPage());
            System.out.println(response.getImageUrl());
            System.out.println(response.getContent());
        }
    }
}
