package com.futurenet.sorisoopbackend.tts.application;

import com.futurenet.sorisoopbackend.tts.domain.TtsRepository;
import com.futurenet.sorisoopbackend.tts.dto.TtsDto;
import com.futurenet.sorisoopbackend.tts.dto.request.GetTtsRequest;
import com.futurenet.sorisoopbackend.tts.dto.response.GetTtsResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
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

    //todo: tts 초기 요청
    @Override
    @Transactional
    public GetTtsResponse createTts(GetTtsRequest request, Long profileId) {

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
            throw new RuntimeException("Python 서버 응답 없음");
        }

        GetTtsResponse body = response.getBody();

        for (GetTtsResponse.TtsResult r : body.getResults()) {
            byte[] audioBytes = Base64.getDecoder().decode(r.getAudio_base64());
            System.out.println("Page " + r.getPage()
                    + " audio size=" + audioBytes.length);
        }
        return body;
    }


    
//    //todo: 생성 동화 읽기 수정
//    @Override
//    @Transactional
//    public GetTtsResponse createCustomTts(GetCustomTtsRequest request, Long profileId) {
//
//        List<TtsDto> result = ttsRepository.getCustomFairyTaleList(request.getCustomFairyTaleId());
//
//        String contentsJson;
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            contentsJson = objectMapper.writeValueAsString(result);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("JSON 변환 실패", e); //todo: 커스텀 예외로 변경
//        }
//
//        ResponseEntity<byte[]> response = webClient.post()
//                .uri("/tts")
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(BodyInserters.fromMultipartData("contents", contentsJson)
//                        .with("speaker_key", request.getVoiceUuid())
//                        .with("current_page", "1")
//                        .with("profile_id", profileId))
//                .retrieve()
//                .toEntity(byte[].class)
//                .block();
//
//        if (response == null || response.getBody() == null) {
//            throw new RuntimeException("Python 서버 응답 없음"); //todo: 커스텀 예외로 변경
//        }
//        return new GetTtsResponse(1, response.getBody());
//    }
}