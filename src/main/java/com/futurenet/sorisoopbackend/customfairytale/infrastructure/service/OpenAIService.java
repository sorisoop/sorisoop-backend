package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleDto;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.ConceptResponse;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.util.AIPromptUtil;
import com.futurenet.sorisoopbackend.global.exception.GlobalErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

@Service
public class OpenAIService {

    private final ChatClient chatClient;

    public OpenAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * 동화 시놉시스 생성
     * */
    //@Retryable
    public List<ConceptResponse> generateCustomFairyTaleSynopsis(URL imageUrl, MimeType mimeType, int age) {
        String synopsisPrompt = AIPromptUtil.makeStorySynopsisPrompt(age);

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4o")
                .maxTokens(100)
                .temperature(0.5)
                .build();

        try {
            ChatResponse synopsisResponse = chatClient.prompt()
                    .user(u -> u.text(synopsisPrompt).media(mimeType, imageUrl))
                    .options(options)
                    .call()
                    .chatResponse();

            if (synopsisResponse == null) {
                throw new CustomFairyTaleException(CustomFairyTaleErrorCode.SYNOPSIS_GENERATE_FAIL);
            }

            String resultJson = synopsisResponse.getResult().getOutput().getText();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resultJson, new TypeReference<List<ConceptResponse>>() {});
        } catch (JsonProcessingException e) {
            throw new RestApiException(GlobalErrorCode.JSON_PROCESSING_FAIL);
        }
    }


    /**
     * 등장인물 정보 추출
     * */
    //@Retryable
    public String extractCharacterGuide(String theme, URL imageUrl, MimeType mimeType) {
        String characterGuidePrompt = AIPromptUtil.makeCharacterInfoPrompt(theme);

        ChatResponse characterResponse = chatClient.prompt()
                .user(u -> u.text(characterGuidePrompt).media(mimeType, imageUrl))
                .call()
                .chatResponse();

        if (characterResponse == null) {
            throw new CustomFairyTaleException(CustomFairyTaleErrorCode.OPENAI_CHARACTER_EXTRACT_RESPONSE_NULL);
        }

        return characterResponse.getResult().getOutput().getText();
    }


    /**
     * 사용자 그림 기반 7페이지 분량 대본 생성
     * */
    //@Retryable
    public MakeCustomFairyTaleDto generateCustomFairyTaleScript(String characterGuideImageUrl, int age, String concept) {
        String contentPrompt = AIPromptUtil.makeCustomFairyTaleScriptPrompt(age, concept);

        try {
            URL imageUrl = URI.create(characterGuideImageUrl).toURL();

            ChatResponse chatResponse = chatClient.prompt()
                    .user(u -> u.text(contentPrompt).media(MimeTypeUtils.IMAGE_PNG, imageUrl))
                    .call()
                    .chatResponse();

            if (chatResponse == null) {
                throw new CustomFairyTaleException(CustomFairyTaleErrorCode.OPENAI_SCRIPT_RESPONSE_NULL);
            }

            String resultJson = chatResponse.getResult().getOutput().getText();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resultJson, new TypeReference<MakeCustomFairyTaleDto>() {});

        } catch (JsonProcessingException e) {
            throw new RestApiException(GlobalErrorCode.JSON_PROCESSING_FAIL);
        } catch (MalformedURLException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        }
    }

}
