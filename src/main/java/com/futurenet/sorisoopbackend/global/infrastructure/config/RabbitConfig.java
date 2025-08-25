package com.futurenet.sorisoopbackend.global.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        factory.setConcurrentConsumers(3); // 동시 소비자 수
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(1); // 하나씩 처리
        factory.setErrorHandler(t -> {
            System.err.println("RabbitMQ 예외 발생: " + t.getMessage());
        });
        return factory;
    }

}
