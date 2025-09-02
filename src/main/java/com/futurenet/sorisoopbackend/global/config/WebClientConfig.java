package com.futurenet.sorisoopbackend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean(name = "geminiWebClient")
    public WebClient geminiWebClient(WebClient.Builder builder) {
        return builder
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(20 * 1024 * 1024))
                .build();
    }

    // tts 서버
    @Bean(name = "ttsWebClient")
    public WebClient ttsWebClient(WebClient.Builder builder) {
        return builder
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(20 * 1024 * 1024))
//                .baseUrl("https://legible-kind-dingo.ngrok-free.app")      // colab 서버
                //.baseUrl("http://1.208.108.242:61569")                  //gpu 서버 구형
                .baseUrl("http://211.105.112.143:61084")                  //gpu 서버 신형
                .build();
    }
}
