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
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class MakeFairyTaleServiceImpl implements MakeFairyTaleService {

    private final AmazonS3Service amazonS3Service;
    private final ChatClient chatClient;
    private final OpenAiImageModel openAiImageModel;

    public MakeFairyTaleServiceImpl(AmazonS3Service amazonS3Service, ChatClient.Builder chatClientBuilder, OpenAiImageModel openAiImageModel) {
        this.amazonS3Service = amazonS3Service;
        this.chatClient = chatClientBuilder.build();
        this.openAiImageModel = openAiImageModel;
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
                    .user(u -> u.text(prompt).media(mimeType, imageUrl))
                    .call()
                    .chatResponse();

            if (chatResponse == null) {
                throw new CustomFairyTaleException(CustomFairyTaleErrorCode.OPENAI_SCRIPT_RESPONSE_NULL);
            }

            String resultJson = chatResponse.getResult().getOutput().getText();

            ObjectMapper objectMapper = new ObjectMapper();
            List<MakeCustomFairyTaleResponse> pages = objectMapper.readValue(resultJson, new TypeReference<List<MakeCustomFairyTaleResponse>>() {});

            for (MakeCustomFairyTaleResponse page : pages) {
                String imagePrompt = OpenAIPromptUtil.makeCustomFairyTaleImagePrompt(page.getContent());

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

        } catch (MalformedURLException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        } catch (JsonProcessingException e) {
            throw new RestApiException(GlobalErrorCode.JSON_PROCESSING_FAIL);
        }
    }
}
