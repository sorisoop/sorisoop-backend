package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.domain.TtsRepository;
import com.futurenet.sorisoopbackend.tts.dto.response.GetVoiceUuidResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TtsServiceImpl implements TtsService {

    private final TtsRepository ttsRepository;
    private final WebClient webClient;

    @Override
    public GetVoiceUuidResponse addSpeakers(Long voiceId) {
        String fileUrl = ttsRepository.getVoiceUrlById(voiceId);

        return webClient.post()
                .uri("/voice/tts/speakers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("fileUrl", fileUrl))
                .retrieve()
                .bodyToMono(GetVoiceUuidResponse.class)
                .block(Duration.ofSeconds(30));

    }

}