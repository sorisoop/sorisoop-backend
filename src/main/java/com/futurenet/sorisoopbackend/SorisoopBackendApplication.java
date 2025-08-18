package com.futurenet.sorisoopbackend;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class SorisoopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SorisoopBackendApplication.class, args);
    }

}
