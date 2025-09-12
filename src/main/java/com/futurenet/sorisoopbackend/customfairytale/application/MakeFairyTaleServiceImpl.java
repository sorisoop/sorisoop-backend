package com.futurenet.sorisoopbackend.customfairytale.application;

import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.domain.CustomFairyTaleContentRepository;
import com.futurenet.sorisoopbackend.customfairytale.domain.CustomFairyTaleRepository;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleContentDto;
import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleDto;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.SaveCustomFairyTaleContentRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.SaveCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleConceptResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.ConceptResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResult;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.service.GeminiService;
import com.futurenet.sorisoopbackend.customfairytale.infrastructure.service.OpenAIService;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.exception.GlobalErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MakeFairyTaleServiceImpl implements MakeFairyTaleService {

    private final AmazonS3Service amazonS3Service;
    private final OpenAIService openAIService;
    private final ProfileRepository profileRepository;
    private final CustomFairyTaleRepository customFairyTaleRepository;
    private final CustomFairyTaleContentRepository customFairyTaleContentRepository;
    private final GeminiService geminiService;

    /**
     * 동화 컨셉 샏성
     * */
    @Override
    public MakeCustomFairyTaleConceptResponse makeSynopsis(MultipartFile image, Long profileId) {

        String savedImageUrl = amazonS3Service.uploadImageAndGeneratePresignedUrl(image, FolderNameConstant.USER_DRAWING)
                .get("presignedUrl");

        FindProfileResponse profileResponse = profileRepository.getProfileByProfileId(profileId);

        URL imageUrl;
        MimeType mimeType;

        try {
            imageUrl = URI.create(savedImageUrl).toURL();
            mimeType = Optional.ofNullable(image.getContentType())
                    .map(MimeType::valueOf)
                    .orElseThrow(() -> new RestApiException(GlobalErrorCode.INVALID_CONTENT_TYPE));
        } catch (MalformedURLException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        }

        List<ConceptResponse> conceptResponse = openAIService.generateCustomFairyTaleSynopsis(
                imageUrl, mimeType, profileResponse.getAge());

        return new MakeCustomFairyTaleConceptResponse(savedImageUrl, image.getContentType(), conceptResponse);
    }

    /**
     * 동화 생성
     * */
    @Override
    @Transactional
    public MakeCustomFairyTaleResult createCustomFairyTale(MakeCustomFairyTaleRequest request) {

        log.info("서비스 시작");
        FindProfileResponse profileResponse = profileRepository.getProfileByProfileId(request.getProfileId());

        String characterGuide;
        MakeCustomFairyTaleDto dto;
        Long customFairyTaleId;

        try {
            URL imageUrl = URI.create(request.getImageUrl()).toURL();
            MimeType mimeType = Optional.ofNullable(request.getImageContentType())
                    .map(MimeType::valueOf)
                    .orElseThrow(() -> new RestApiException(GlobalErrorCode.INVALID_CONTENT_TYPE));

            characterGuide = openAIService.extractCharacterGuide(imageUrl, mimeType);
            log.info("등장인물 가이드: {}", characterGuide);

            dto = openAIService.generateCustomFairyTaleScript(imageUrl, mimeType, profileResponse.getAge(), request.getConcept());

            log.info("동화 대본 생성 완료");

            SaveCustomFairyTaleRequest saveCustomFairyTaleRequest = SaveCustomFairyTaleRequest.of(dto, request.getProfileId(), request.getImageUrl());
            int result = customFairyTaleRepository.saveCustomFairyTale(saveCustomFairyTaleRequest);

            if (result == 0) {
                throw new CustomFairyTaleException(CustomFairyTaleErrorCode.SAVE_CUSTOM_FAIRY_TALE_FAIL);
            }

            customFairyTaleId = saveCustomFairyTaleRequest.getId();

        } catch (MalformedURLException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        }

        String characterReferenceImage = geminiService.generateReferenceImageBase64(characterGuide);

        List<MakeCustomFairyTaleContentDto> completedPages = geminiService.generateImages(dto.getPages(), characterGuide, characterReferenceImage);

        List<SaveCustomFairyTaleContentRequest> contentRequests = completedPages.stream()
                .map(page -> new SaveCustomFairyTaleContentRequest(
                        customFairyTaleId,
                        page.getPage(),
                        page.getImageUrl(),
                        page.getContentKr()
                ))
                .toList();

        int result = customFairyTaleContentRepository.saveCustomFairyTaleContents(contentRequests);

        if (result != contentRequests.size()) {
            throw new CustomFairyTaleException(CustomFairyTaleErrorCode.SAVE_CUSTOM_FAIRY_TALE_CONTENT_FAIL);
        }

        return new MakeCustomFairyTaleResult(
                customFairyTaleId,
                completedPages.stream()
                        .map(page -> new MakeCustomFairyTaleContentResponse(
                                page.getPage(),
                                dto.getTitle(),
                                page.getImageUrl(),
                                page.getContentKr()
                        ))
                        .toList()
        );
    }
}