package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.domain.TtsRepository;
import com.futurenet.sorisoopbackend.tts.dto.TtsDto;
import com.futurenet.sorisoopbackend.tts.dto.request.GetCustomTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class  TtsServiceImpl implements TtsService {

    private final TtsRepository ttsRepository;
    private final WebClient webClient;

    public TtsServiceImpl(TtsRepository ttsRepository,
                          @Qualifier("ttsWebClient") WebClient webClient) {
        this.ttsRepository = ttsRepository;
        this.webClient = webClient;
    }

    @Override
    @Transactional
    public void createTts(GetTtsRequest request, Long profileId) {

        List<TtsDto> result = ttsRepository.getFairyTaleList(request.getFairyTaleId());

        List<Map<String, Object>> pages = result.stream()
                .map(dto -> Map.<String, Object>of(
                        "page", dto.getPage(),
                        "script", dto.getScript()
                ))
                .toList();

        Map<String, Object> requestBody = Map.of(
                "fairy_tale_id", request.getFairyTaleId(),
                "profile_id", profileId,
                "voice_id", request.getSpeakerId(),
                "pages", pages
        );

        ResponseEntity<GetTtsResponse> response = webClient.post()
                .uri("/tts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .toEntity(GetTtsResponse.class)
                .block();

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Python 서버 응답 없음"); //todo: 사용자 예외로 변경
        }
    }

    @Override
    public GetTtsResponse getTts(String speakerId, Long fairyTaleId, int page, Long profileId) {

        ResponseEntity<byte[]> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tts")
                        .queryParam("fairy_tale_id", fairyTaleId)
                        .queryParam("profile_id", profileId)
                        .queryParam("voice_id", speakerId)
                        .queryParam("page", page)
                        .build())
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .toEntity(byte[].class)
                .block();

        if (result == null || result.getBody() == null) {
            throw new RuntimeException("Python 서버 응답 없음"); // TODO: 커스텀 예외로 변경
        }
        return new GetTtsResponse(page, result.getBody());
    }


    @Override
    @Transactional
    public void createCustomTts(GetCustomTtsRequest request, Long profileId) {

        List<TtsDto> result = ttsRepository.getCustomFairyTaleList(request.getCustomFairyTaleId());

        List<Map<String, Object>> pages = result.stream()
                .map(dto -> Map.<String, Object>of(
                        "page", dto.getPage(),
                        "script", dto.getScript()
                ))
                .toList();

        Map<String, Object> requestBody = Map.of(
                "fairy_tale_id", request.getCustomFairyTaleId(),
                "profile_id", profileId,
                "voice_id", request.getSpeakerId(),
                "pages", pages
        );

        ResponseEntity<GetTtsResponse> response = webClient.post()
                .uri("/tts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .toEntity(GetTtsResponse.class)
                .block();

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Python 서버 응답 없음"); //todo: 사용자 예외로 변경
        }
    }

    @Override
    public GetTtsResponse getCustomTts(String speakerId, Long customFairyTaleId, int page, Long profileId) {
        ResponseEntity<byte[]> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tts")
                        .queryParam("fairy_tale_id", customFairyTaleId)
                        .queryParam("profile_id", profileId)
                        .queryParam("voice_id", speakerId)
                        .queryParam("page", page)
                        .build())
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .toEntity(byte[].class)
                .block();

        if (result == null || result.getBody() == null) {
            throw new RuntimeException("Python 서버 응답 없음"); // TODO: 커스텀 예외로 변경
        }
        return new GetTtsResponse(page, result.getBody());
    }
}