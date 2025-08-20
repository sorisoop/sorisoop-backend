package com.futurenet.sorisoopbackend.profile.application;

import com.futurenet.sorisoopbackend.profile.dto.request.SaveProfileRequest;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ProfileService {
    List<FindProfileResponse> getAllProfilesByMemberId(Long memberId);
    void saveProfile(SaveProfileRequest request, Long memberId);
    void selectProfile(Long profileId, Long memberId, HttpServletRequest request, HttpServletResponse response);
}
