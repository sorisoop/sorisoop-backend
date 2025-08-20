package com.futurenet.sorisoopbackend.profile.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.profile.application.ProfileService;
import com.futurenet.sorisoopbackend.profile.dto.request.SaveProfileRequest;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<?> getAllProfiles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FindProfileResponse> result = profileService.getAllProfilesByMemberId(userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("PR100", "프로필 조회 성공", result));
    }

    @PostMapping
    public ResponseEntity<?> saveProfile(@Valid @ModelAttribute SaveProfileRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileService.saveProfile(request, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("PR101", "프로필 등록 성공", null));
    }
}
