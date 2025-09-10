package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.application.exception.TtsErrorCode;
import com.futurenet.sorisoopbackend.tts.application.exception.TtsException;
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
        if (result == null || result.isEmpty()) {
            throw new TtsException(TtsErrorCode.FAIRY_TALE_NOT_FOUND);
        }

        if (request.getSpeakerId() == null || profileId == null) {
            throw new TtsException(TtsErrorCode.INVALID_REQUEST);
        }

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

        ResponseEntity<GetTtsResponse> response;

        try{
            response = webClient.post()
                    .uri("/tts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .toEntity(GetTtsResponse.class)
                    .block();
        } catch (Exception e) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_ERROR);
        }

        if (response == null || response.getBody() == null) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_NO_RESPONSE);
        }
    }

    @Override
    public GetTtsResponse getTts(String speakerId, Long fairyTaleId, int page, Long profileId) {
        if (page <= -1) {
            throw new TtsException(TtsErrorCode.INVALID_PAGE_REQUEST);
        }
        if (speakerId == null || fairyTaleId == null || profileId == null) {
            throw new TtsException(TtsErrorCode.INVALID_REQUEST);
        }

        ResponseEntity<byte[]> result;

        try{
            result = webClient.get()
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
        } catch (Exception e) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_ERROR);
        }

        if (result == null || result.getBody() == null) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_NO_RESPONSE);
        }

        if (result.getBody().length == 0) {
            throw new TtsException(TtsErrorCode.TTS_FETCH_FAIL);
        }

        return new GetTtsResponse(page, result.getBody());
    }


    @Override
    @Transactional
    public void createCustomTts(GetCustomTtsRequest request, Long profileId) {

        List<TtsDto> result = ttsRepository.getCustomFairyTaleList(request.getCustomFairyTaleId());
        if (result == null || result.isEmpty()) {
            throw new TtsException(TtsErrorCode.FAIRY_TALE_NOT_FOUND);
        }

        if (request.getSpeakerId() == null || profileId == null) {
            throw new TtsException(TtsErrorCode.INVALID_REQUEST);
        }

        List<Map<String, Object>> pages = result.stream()
                .map(dto -> Map.<String, Object>of(
                        "page", dto.getPage()+1,
                        "script", dto.getScript()
                ))
                .toList();

        Map<String, Object> requestBody = Map.of(
                "fairy_tale_id", request.getCustomFairyTaleId(),
                "profile_id", profileId,
                "voice_id", request.getSpeakerId(),
                "pages", pages
        );

        ResponseEntity<GetTtsResponse> response;

        try{
            response = webClient.post()
                    .uri("/tts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .toEntity(GetTtsResponse.class)
                    .block();
        } catch (Exception e) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_ERROR);
        }

        if (response == null || response.getBody() == null) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_NO_RESPONSE);
        }
    }

    @Override
    public GetTtsResponse getCustomTts(String speakerId, Long customFairyTaleId, int page, Long profileId) {
        if (page <= 0) {
            throw new TtsException(TtsErrorCode.INVALID_PAGE_REQUEST);
        }
        if (speakerId == null || customFairyTaleId == null || profileId == null) {
            throw new TtsException(TtsErrorCode.INVALID_REQUEST);
        }

        ResponseEntity<byte[]> result;
        try{
            result = webClient.get()
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
        } catch (Exception e) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_ERROR);
        }

        if (result == null || result.getBody() == null) {
            throw new TtsException(TtsErrorCode.PYTHON_SERVER_NO_RESPONSE);
        }

        if (result.getBody().length == 0) {
            throw new TtsException(TtsErrorCode.TTS_FETCH_FAIL);
        }
        return new GetTtsResponse(page, result.getBody());
    }
}