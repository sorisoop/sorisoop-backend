package com.futurenet.sorisoopbackend.customfairytale.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResponse;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.util.OpenAIPromptUtil;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.exception.GlobalErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class MakeFairyTaleServiceImpl implements MakeFairyTaleService {

    private final AmazonS3Service amazonS3Service;
    private final ChatClient chatClient;

    public MakeFairyTaleServiceImpl(AmazonS3Service amazonS3Service, ChatClient.Builder chatClientBuilder) {
        this.amazonS3Service = amazonS3Service;
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public List<MakeCustomFairyTaleResponse> createCustomFairyTale(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            throw new CustomFairyTaleException(CustomFairyTaleErrorCode.IMAGE_FILE_NULL);
        }

        String userImageUrl = amazonS3Service.uploadImage(image, FolderNameConstant.USER_DRAWING);
        String prompt = OpenAIPromptUtil.makeCustomFairyTaleScriptPrompt(5);

        try {
            URL imageUrl = URI.create(userImageUrl).toURL();
            MimeType mimeType = Optional.ofNullable(image.getContentType())
                    .map(MimeType::valueOf)
                    .orElseThrow(() -> new RestApiException(GlobalErrorCode.INVALID_CONTENT_TYPE));

            ChatResponse chatResponse = chatClient.prompt()
                    .user(u -> u
                            .text(prompt)
                            .media(mimeType, imageUrl)
                    )
                    .call()
                    .chatResponse();

            String resultJson = chatResponse.getResult().getOutput().getText();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resultJson, new TypeReference<List<MakeCustomFairyTaleResponse>>() {});

        } catch (MalformedURLException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        } catch (JsonProcessingException e) {
            throw new RestApiException(GlobalErrorCode.JSON_PROCESSING_FAIL);
        }
    }
}
