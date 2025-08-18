package com.futurenet.sorisoopbackend.global.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String FAIRYTALE_QUEUE = "fairytale.queue";
    public static final String FAIRYTALE_EXCHANGE = "fairytale.exchange";
    public static final String FAIRYTALE_ROUTING_KEY = "fairytale.create";

    @Bean
    public Queue fairyTaleQueue() {
        return new Queue(FAIRYTALE_QUEUE, true);
    }

    @Bean
    public TopicExchange fairyTaleExchange() {
        return new TopicExchange(FAIRYTALE_EXCHANGE);
    }

    @Bean
    public Binding fairyTaleBinding() {
        return BindingBuilder
                .bind(fairyTaleQueue())
                .to(fairyTaleExchange())
                .with(FAIRYTALE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
