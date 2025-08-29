package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleContentDto;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.util.AIPromptUtil;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final AmazonS3Service amazonS3Service;

    @Value("${gemini.api-key}")
    private String apiKey;

    public static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-image-preview:generateContent?key=";

    public List<MakeCustomFairyTaleContentDto> generateImages(List<MakeCustomFairyTaleContentDto> pages, String characterGuide) {
        RestTemplate restTemplate = new RestTemplate();

        for (MakeCustomFairyTaleContentDto page : pages) {
            String prompt = AIPromptUtil.makeCustomFairyTaleImagePrompt(characterGuide, page.getSceneType(), page.getEmotion(), page.getContentEn());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    GEMINI_API_URL + apiKey,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            try {
                List<?> candidates = (List<?>) response.getBody().get("candidates");
                if (candidates == null || candidates.isEmpty()) {
                    throw new IllegalStateException("No candidates returned from Gemini API");
                }

                Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
                Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
                List<?> parts = (List<?>) content.get("parts");

                Optional<Map<String, Object>> imagePartOpt = parts.stream()
                        .filter(p -> p instanceof Map && ((Map<?, ?>) p).get("inlineData") != null)
                        .map(p -> (Map<String, Object>) p)
                        .findFirst();

                if (imagePartOpt.isEmpty()) {
                    throw new IllegalStateException("No inlineData found in parts");
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

            } catch (Exception e) {
                throw new RuntimeException("Gemini 이미지 생성 실패", e);
            }
        }

        return pages;
    }

}
