package com.futurenet.sorisoopbackend.customfairytale.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleDto;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResponse;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.util.OpenAIPromptUtil;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.exception.GlobalErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    public List<MakeCustomFairyTaleResponse> createCustomFairyTale(MakeCustomFairyTaleRequest request) {

//        if (image == null || image.isEmpty()) {
//            throw new CustomFairyTaleException(CustomFairyTaleErrorCode.IMAGE_FILE_NULL);
//        }

     //   String userImageUrl = amazonS3Service.uploadImage(image, FolderNameConstant.USER_DRAWING);

        String characterGuide;
        List<MakeCustomFairyTaleDto> pages;

        try {
            URL imageUrl = URI.create(request.getImageUrl()).toURL();
            MimeType mimeType = Optional.ofNullable(request.getImageContentType())
                    .map(MimeType::valueOf)
                    .orElseThrow(() -> new RestApiException(GlobalErrorCode.INVALID_CONTENT_TYPE));

            characterGuide = extractCharacterGuide(imageUrl, mimeType);
            log.info("characterGuide1: {}", characterGuide);
            pages = generateCustomFairyTaleScript(imageUrl, mimeType);

        } catch (MalformedURLException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        }

        return generateCustomFairyTaleImage(pages, characterGuide).stream()
                .map(page -> new MakeCustomFairyTaleResponse(
                        page.getPage(),
                        page.getImageUrl(),
                        page.getContent_kr()
                ))
                .toList();
    }

    /**
     * 등장인물 정보 추출
     * */
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
    public List<MakeCustomFairyTaleDto> generateCustomFairyTaleScript(URL imageUrl, MimeType mimeType) {
        String contentPrompt = OpenAIPromptUtil.makeCustomFairyTaleScriptPrompt(5);

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
            return objectMapper.readValue(resultJson, new TypeReference<List<MakeCustomFairyTaleDto>>() {});

        } catch (JsonProcessingException e) {
            throw new RestApiException(GlobalErrorCode.JSON_PROCESSING_FAIL);
        }
    }


    /**
     * 동화 이미지 생성
     * */
    public List<MakeCustomFairyTaleDto> generateCustomFairyTaleImage(List<MakeCustomFairyTaleDto> pages, String characterGuide) {

        for (MakeCustomFairyTaleDto page : pages) {

            String imagePrompt = OpenAIPromptUtil.makeCustomFairyTaleImagePrompt(characterGuide, page.getContent_en());

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

                    if (page.getPage() == 1) {
                        URL s3ImageUrl = URI.create(s3Url).toURL();
                        MimeType mimeType = MimeType.valueOf("image/png");
                        characterGuide = extractCharacterGuide(s3ImageUrl, mimeType);

                        log.info("characterGuide2: {}", characterGuide);
                    }
                }
            } catch (Exception e) {
                throw new CustomFairyTaleException(CustomFairyTaleErrorCode.OPENAI_IMAGE_GENERATE_FAIL);
            }
        }

        return pages;
    }

}
