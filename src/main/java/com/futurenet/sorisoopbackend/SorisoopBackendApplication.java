package com.futurenet.sorisoopbackend;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableRabbit
@SpringBootApplication
public class SorisoopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SorisoopBackendApplication.class, args);
    }

}
