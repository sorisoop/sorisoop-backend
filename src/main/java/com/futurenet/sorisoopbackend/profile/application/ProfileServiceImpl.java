package com.futurenet.sorisoopbackend.profile.application;

import com.futurenet.sorisoopbackend.auth.application.exception.AuthErrorCode;
import com.futurenet.sorisoopbackend.auth.application.exception.AuthException;
import com.futurenet.sorisoopbackend.auth.constant.AuthConstants;
import com.futurenet.sorisoopbackend.auth.domain.RefreshTokenRepository;
import com.futurenet.sorisoopbackend.auth.dto.request.SaveRefreshTokenRequest;
import com.futurenet.sorisoopbackend.auth.util.JwtUtil;
import com.futurenet.sorisoopbackend.auth.util.RequestUtil;
import com.futurenet.sorisoopbackend.auth.util.ResponseUtil;
import com.futurenet.sorisoopbackend.global.constant.FolderNameConstant;
import com.futurenet.sorisoopbackend.global.infrastructure.service.AmazonS3Service;
import com.futurenet.sorisoopbackend.member.domain.MemberRepository;
import com.futurenet.sorisoopbackend.profile.application.exception.ProfileErrorCode;
import com.futurenet.sorisoopbackend.profile.application.exception.ProfileException;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.dto.request.SaveProfileRequest;
import com.futurenet.sorisoopbackend.profile.dto.request.UpdateProfileRequest;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AmazonS3Service amazonS3Service;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public List<FindProfileResponse> getAllProfilesByMemberId(Long memberId) {
        return profileRepository.findAllProfilesByMemberId(memberId);
    }

    @Override
    @Transactional
    public void saveProfile(SaveProfileRequest request, Long memberId) {

        String profileImage = null;

        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            profileImage = amazonS3Service.uploadImage(request.getProfileImage(), FolderNameConstant.PROFILE_IMAGE);
        }

        int result = profileRepository.saveProfile(request.toDto(profileImage, memberId));

        if (result == 0) {
            throw new ProfileException(ProfileErrorCode.SAVE_PROFILE_FAIL);
        }
    }

    @Override
    @Transactional
    public void selectProfile(Long profileId, Long memberId, HttpServletRequest request, HttpServletResponse response) {
        boolean exists = profileRepository.existsProfileByMemberIdAndProfileId(memberId, profileId);

        if (!exists) {
            throw new ProfileException(ProfileErrorCode.MISMATCH_PROFILE_ID_AND_MEMBER_ID);
        }

        String deviceId = RequestUtil.getDeviceId(request);
        String newAccessToken = jwtUtil.createAccessToken("access", memberId, profileId, "ROLE_USER", AuthConstants.ACCESS_EXPIRED);
        String newRefreshToken = jwtUtil.createRefreshToken("refresh", memberId, profileId, deviceId, AuthConstants.REFRESH_EXPIRED);

        int result = refreshTokenRepository.saveRefreshToken(new SaveRefreshTokenRequest(newRefreshToken, profileId, memberId, deviceId));

        if (result == 0) {
            throw new AuthException(AuthErrorCode.TOKEN_ISSUE_FAIL);
        }

        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("Authorization", newAccessToken, AuthConstants.ACCESS_COOKIE_EXPIRED));
        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("refresh", newRefreshToken, AuthConstants.REFRESH_COOKIE_EXPIRED));
    }

    @Override
    @Transactional
    public void deleteProfile(Long profileId, Long memberId) {
        int result = profileRepository.deleteProfileByMemberIdAndProfileId(memberId, profileId);

        if (result == 0) {
            throw new ProfileException(ProfileErrorCode.DELETE_PROFILE_FAIL);
        }
    }

    @Override
    @Transactional
    public void updateProfile(UpdateProfileRequest request, Long profileId) {

        String profileImage = null;

        if (request.getProfileImage() != null) {
            profileImage = amazonS3Service.uploadImage(request.getProfileImage(), FolderNameConstant.PROFILE_IMAGE);
        }

        int result = profileRepository.updateProfile(request.toDto(profileId, profileImage));

        if (result == 0) {
            throw new ProfileException(ProfileErrorCode.UPDATE_PROFILE_FAIL);
        }
    }

    @Override
    public FindProfileResponse getProfileById(Long profileId) {
        FindProfileResponse result = profileRepository.getProfileByProfileId(profileId);

        if (result == null) {
            throw new ProfileException(ProfileErrorCode.FIND_PROFILE_FAIL);
        }

        return result;
    }
}
