package com.futurenet.sorisoopbackend.customfairytale.application;

import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleDto;
import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleConceptResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.ConceptResponse;
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

    @Override
    public MakeCustomFairyTaleConceptResponse makeSynopsis(MultipartFile image, Long profileId) {

        String savedImageUrl = amazonS3Service.uploadImage(image, FolderNameConstant.USER_DRAWING);
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

        List<ConceptResponse> conceptResponse = openAIService.generateCustomFairyTaleSynopsis(imageUrl, mimeType, profileResponse.getAge());
        return new MakeCustomFairyTaleConceptResponse(savedImageUrl, image.getContentType(), conceptResponse);
    }

    @Override
    @Transactional
    public List<MakeCustomFairyTaleResponse> createCustomFairyTale(MakeCustomFairyTaleRequest request) {

        String characterGuide;
        List<MakeCustomFairyTaleDto> pages;

        try {
            URL imageUrl = URI.create(request.getImageUrl()).toURL();
            MimeType mimeType = Optional.ofNullable(request.getImageContentType())
                    .map(MimeType::valueOf)
                    .orElseThrow(() -> new RestApiException(GlobalErrorCode.INVALID_CONTENT_TYPE));

            characterGuide = openAIService.extractCharacterGuide(imageUrl, mimeType);
            log.info("characterGuide1: {}", characterGuide);
            pages = openAIService.generateCustomFairyTaleScript(imageUrl, mimeType);

        } catch (MalformedURLException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        }

        return openAIService.generateCustomFairyTaleImage(pages, characterGuide).stream()
                .map(page -> new MakeCustomFairyTaleResponse(
                        page.getPage(),
                        page.getImageUrl(),
                        page.getContent_kr()
                ))
                .toList();
    }


}
