package com.futurenet.sorisoopbackend.profile.application;

import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.dto.request.SaveProfileRequest;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final AmazonS3Service amazonS3Service;

    @Override
    public List<FindProfileResponse> getAllProfilesByMemberId(Long memberId) {
        return profileRepository.findAllProfilesByMemberId(memberId);
    }

    @Override
    @Transactional
    public void saveProfile(SaveProfileRequest request, Long memberId) {
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {

        }
    }
}
