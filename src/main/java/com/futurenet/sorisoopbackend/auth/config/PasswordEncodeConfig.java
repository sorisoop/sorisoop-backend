package com.futurenet.sorisoopbackend.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncodeConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(AuthenticationConfiguration configuration) {
        return new BCryptPasswordEncoder();
    }
}
