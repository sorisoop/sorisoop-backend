package com.futurenet.sorisoopbackend.tts.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.tts.domain.TtsRepository;
import com.futurenet.sorisoopbackend.tts.dto.TtsDto;
import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;
import com.futurenet.sorisoopbackend.tts.dto.response.GetVoiceUuidResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TtsServiceImpl implements TtsService {

    private final TtsRepository ttsRepository;
    private final WebClient webClient;

    @Override
    @Transactional
    public GetVoiceUuidResponse addSpeakers(Long voiceId) {
        String fileUrl = ttsRepository.getVoiceUrlById(voiceId);

        return webClient.post()
                .uri("/tts/voices")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("fileUrl", fileUrl))
                .retrieve()
                .bodyToMono(GetVoiceUuidResponse.class)
                .block(Duration.ofSeconds(30));
    }

    @Override
    @Transactional
    public GetTtsResponse createTts(GetTtsRequest request) {

        List<TtsDto> result = ttsRepository.getFairyTaleList(request.getFairyTaleId());

        String contentsJson;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            contentsJson = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e); //todo: 커스텀 예외로 변경
        }

        ResponseEntity<byte[]> response = webClient.post()
                .uri("/tts")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("contents", contentsJson)
                        .with("speaker_key", request.getVoiceUuid())
                        .with("current_page", "1"))
                .retrieve()
                .toEntity(byte[].class)
                .block();

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Python 서버 응답 없음"); //todo: 커스텀 예외로 변경
        }
        return new GetTtsResponse(1, response.getBody());
    }

    @Override
    public GetTtsResponse getTts(String voiceUuid, int page) {
        ResponseEntity<byte[]> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tts/page")
                        .queryParam("speaker_key", voiceUuid)
                        .queryParam("page", page)
                        .build())
                .accept(MediaType.APPLICATION_OCTET_STREAM) // 오디오 바이너리 받기
                .retrieve()
                .toEntity(byte[].class)
                .block();

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Python 서버 응답 없음"); // TODO: 커스텀 예외로 변경
        }

        return new GetTtsResponse(page, response.getBody());
    }


}