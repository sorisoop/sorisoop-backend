package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleContentDto;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.util.AIPromptUtil;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.*;

@Slf4j
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

    public List<MakeCustomFairyTaleContentDto> generateImages(List<MakeCustomFairyTaleContentDto> pages, String characterGuide, String characterReferenceImage) {
        log.info("동화 이미지 생성 시작");
        return Flux.fromIterable(pages)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(page -> generateImageMono(page, characterGuide, characterReferenceImage))
                .sequential()
                .collectList()
                .block();
    }


    private Mono<MakeCustomFairyTaleContentDto> generateImageMono(MakeCustomFairyTaleContentDto page, String characterGuide, String characterReferenceImage) {
        String prompt = AIPromptUtil.makeCustomFairyTaleImagePrompt(characterGuide, page.getSceneType(), page.getEmotion(), page.getContentEn(), page.getAction());

        List<Map<String, Object>> parts = new ArrayList<>();
        parts.add(Map.of("text", prompt));
        if (characterReferenceImage != null) {
            parts.add(Map.of("inlineData", Map.of(
                    "mimeType", "image/png",
                    "data", characterReferenceImage
            )));
        }

        Map<String, Object> body = Map.of("contents", List.of(Map.of(
                "role", "user",
                "parts", parts
        )));

//        return webClient.post()
//                .uri(GEMINI_API_URL + apiKey)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(body)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
//                .map(this::extractBase64FromResponse)
//                .flatMap(base64 -> {
//                    byte[] imageBytes = Base64.getDecoder().decode(base64);
//                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
//                    String s3Url = amazonS3Service.uploadImage(inputStream, FolderNameConstant.CUSTOM_FAIRY_TALE, "png");
//                    page.setImageUrl(s3Url);
//                    return Mono.just(page);
//                });
        return webClient.post()
                .uri(GEMINI_API_URL + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .flatMap(response -> {
                    String base64 = extractBase64FromResponse(response);
                    if (base64 == null) {
                        log.error("Gemini 응답에서 이미지 base64 데이터를 찾을 수 없습니다 (page: {})", page.getPage());
                        return Mono.error(new CustomFairyTaleException(CustomFairyTaleErrorCode.GEMINI_IMAGE_GENERATE_FAIL));
                    }

                    try {
                        byte[] imageBytes = Base64.getDecoder().decode(base64);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                        String s3Url = amazonS3Service.uploadImage(inputStream, FolderNameConstant.CUSTOM_FAIRY_TALE, "png");
                        page.setImageUrl(s3Url);
                        return Mono.just(page);
                    } catch (Exception e) {
                        log.error("이미지 디코딩 또는 업로드 실패 (page: {})", page.getPage(), e);
                        return Mono.error(new CustomFairyTaleException(CustomFairyTaleErrorCode.GEMINI_IMAGE_GENERATE_FAIL));
                    }
                })
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2)) // 최대 3회, 2초 간격 지수 백오프
                                .filter(ex -> ex instanceof CustomFairyTaleException || ex instanceof RuntimeException)
                                .onRetryExhaustedThrow((retryBackoffSpec, signal) -> signal.failure())
                )
                .onErrorResume(ex -> {
                    log.error("이미지 생성 실패 (page: {}) - 재시도 후에도 실패: {}", page.getPage(), ex.getMessage());
                    return Mono.just(page);
                });
    }

    public String generateReferenceImageBase64(String characterGuide) {
        log.info("가이드 이미지 생성 시작");
        String prompt = AIPromptUtil.makeReferenceImagePrompt(characterGuide);

        Map<String, Object> body = Map.of(
                "contents", List.of(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", prompt))
                ))
        );

        try {
            return webClient.post()
                    .uri(GEMINI_API_URL + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .map(this::extractBase64FromResponse)
                    .block();
        } catch (Exception e) {
            log.warn("error: Reference image generation failed, fallback to text-only image prompt");
            return null;
        }
    }

    private String extractBase64FromResponse(Map<String, Object> response) {
        try {
            List<?> candidates = (List<?>) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                log.error("error: No candidate found in response");
                return null;
            }

            Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
            Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
            List<?> parts = (List<?>) content.get("parts");

            Optional<Map<String, Object>> imagePartOpt = parts.stream()
                    .filter(p -> p instanceof Map && ((Map<?, ?>) p).get("inlineData") != null)
                    .map(p -> (Map<String, Object>) p)
                    .findFirst();

            if (imagePartOpt.isEmpty()) {
                log.error("error: image not found");
                return null;
            }

            Map<String, Object> inlineData = (Map<String, Object>) imagePartOpt.get().get("inlineData");
            return (String) inlineData.get("data");

        } catch (Exception e) {
            throw new CustomFairyTaleException(CustomFairyTaleErrorCode.GEMINI_IMAGE_GENERATE_FAIL);
        }
    }
}