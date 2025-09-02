package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleContentDto;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.util.AIPromptUtil;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayInputStream;
import java.util.*;

@Service
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    public static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-image-preview:generateContent?key=";

    private final AmazonS3Service amazonS3Service;
    private final WebClient webClient;

    public GeminiService(AmazonS3Service amazonS3Service,
                         @Qualifier("geminiWebClient") WebClient webClient) {
        this.amazonS3Service = amazonS3Service;
        this.webClient = webClient;
    }


    public List<MakeCustomFairyTaleContentDto> generateImages(List<MakeCustomFairyTaleContentDto> pages, String characterGuide) {
        return Flux.fromIterable(pages)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(page -> generateImageMono(page, characterGuide))
                .sequential()
                .collectList()
                .block();
    }


    private Mono<MakeCustomFairyTaleContentDto> generateImageMono(MakeCustomFairyTaleContentDto page, String characterGuide) {
        String prompt = AIPromptUtil.makeCustomFairyTaleImagePrompt(
                characterGuide,
                page.getSceneType(),
                page.getEmotion(),
                page.getContentEn()
        );

        Map<String, Object> body = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", prompt)))
                )
        );

        return webClient.post()
                .uri(GEMINI_API_URL + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .flatMap(response -> {
                    try {
                        List<?> candidates = (List<?>) response.get("candidates");
                        if (candidates == null || candidates.isEmpty()) {
                            return Mono.error(new CustomFairyTaleException(CustomFairyTaleErrorCode.GEMINI_IMAGE_GENERATE_FAIL));
                        }

                        Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
                        Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
                        List<?> parts = (List<?>) content.get("parts");

                        Optional<Map<String, Object>> imagePartOpt = parts.stream()
                                .filter(p -> p instanceof Map && ((Map<?, ?>) p).get("inlineData") != null)
                                .map(p -> (Map<String, Object>) p)
                                .findFirst();

                        if (imagePartOpt.isEmpty()) {
                            return Mono.error(new CustomFairyTaleException(CustomFairyTaleErrorCode.GEMINI_IMAGE_GENERATE_FAIL));
                        }

                        Map<String, Object> inlineData = (Map<String, Object>) imagePartOpt.get().get("inlineData");
                        String base64 = (String) inlineData.get("data");

                        byte[] imageBytes = Base64.getDecoder().decode(base64);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

                        String s3Url = amazonS3Service.uploadImage(
                                inputStream,
                                FolderNameConstant.CUSTOM_FAIRY_TALE,
                                "png"
                        );

                        page.setImageUrl(s3Url);
                        return Mono.just(page);

                    } catch (Exception e) {
                        return Mono.error(new CustomFairyTaleException(CustomFairyTaleErrorCode.GEMINI_IMAGE_GENERATE_FAIL));
                    }
                });
    }
}