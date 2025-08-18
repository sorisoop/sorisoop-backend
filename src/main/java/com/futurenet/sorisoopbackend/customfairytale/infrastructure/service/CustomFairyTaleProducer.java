package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.global.infrastructure.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomFairyTaleProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMakeFairyTaleRequest(MakeCustomFairyTaleRequest request) {

        rabbitTemplate.convertAndSend(
                RabbitConfig.FAIRYTALE_EXCHANGE,
                RabbitConfig.FAIRYTALE_ROUTING_KEY,
                request
        );
    }
}
