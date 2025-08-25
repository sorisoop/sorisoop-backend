package com.futurenet.sorisoopbackend.customfairytale.infrastructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleContentDto;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleDto;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.ConceptResponse;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.util.OpenAIPromptUtil;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.exception.GlobalErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

@Service
public class OpenAIService {

    private final ChatClient chatClient;
    private final OpenAiImageModel openAiImageModel;
    private final AmazonS3Service amazonS3Service;

    public OpenAIService(ChatClient.Builder chatClientBuilder, OpenAiImageModel openAiImageModel, AmazonS3Service amazonS3Service) {
        this.chatClient = chatClientBuilder.build();
        this.openAiImageModel = openAiImageModel;
        this.amazonS3Service = amazonS3Service;
    }

    /**
     * 동화 시놉시스 생성
     * */
    //@Retryable
    public List<ConceptResponse> generateCustomFairyTaleSynopsis(URL imageUrl, MimeType mimeType, int age) {
        String synopsisPrompt = OpenAIPromptUtil.makeStorySynopsisPrompt(age);

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
    public String extractCharacterGuide(URL imageUrl, MimeType mimeType) {
        String characterGuidePrompt = OpenAIPromptUtil.makeCharacterInfoPrompt();

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
    public MakeCustomFairyTaleDto generateCustomFairyTaleScript(URL imageUrl, MimeType mimeType, int age, String concept) {
        String contentPrompt = OpenAIPromptUtil.makeCustomFairyTaleScriptPrompt(age, concept);

        try {
            ChatResponse chatResponse = chatClient.prompt()
                    .user(u -> u.text(contentPrompt).media(mimeType, imageUrl))
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
        }
    }


    /**
     * 동화 이미지 생성
     * */
    //@Retryable
    public List<MakeCustomFairyTaleContentDto> generateCustomFairyTaleImage(List<MakeCustomFairyTaleContentDto> pages, String characterGuide) {

        for (MakeCustomFairyTaleContentDto page : pages) {

            String imagePrompt = OpenAIPromptUtil.makeCustomFairyTaleImagePrompt(
                    characterGuide,
                    page.getContentEn(),
                    page.getSceneType(),
                    page.getEmotion()
            );

            try {
                ImageResponse imageResponse = openAiImageModel.call(
                        new ImagePrompt(imagePrompt, OpenAiImageOptions.builder()
                                .quality("standard")
                                .N(1)
                                .width(1024)
                                .height(1024)
                                .build()
                        )
                );

                String imageUrlFromOpenAI = imageResponse.getResult().getOutput().getUrl();

                try (InputStream openAiImageStream = URI.create(imageUrlFromOpenAI).toURL().openStream()) {
                    String s3Url = amazonS3Service.uploadImage(
                            openAiImageStream,
                            FolderNameConstant.CUSTOM_FAIRY_TALE,
                            "png"
                    );
                    page.setImageUrl(s3Url);
                }
            } catch (Exception e) {
                throw new CustomFairyTaleException(CustomFairyTaleErrorCode.OPENAI_IMAGE_GENERATE_FAIL);
            }
        }

        return pages;
    }
}
