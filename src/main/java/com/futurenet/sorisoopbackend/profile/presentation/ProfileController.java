package com.futurenet.sorisoopbackend.profile.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.profile.application.ProfileService;
import com.futurenet.sorisoopbackend.profile.dto.request.SaveProfileRequest;
import com.futurenet.sorisoopbackend.profile.dto.request.UpdateProfileRequest;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/{profileId}")
    public ResponseEntity<?> selectProfile(@PathVariable Long profileId, @AuthenticationPrincipal UserPrincipal userPrincipal,
                                           HttpServletRequest request, HttpServletResponse response) {
        profileService.selectProfile(profileId, userPrincipal.getId(), request, response);
        return ResponseEntity.ok(new ApiResponse<>("PR102", "프로필 선택 성공", null));
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long profileId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileService.deleteProfile(profileId, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("PR103", "프로필 삭제 성공", null));
    }

    @PatchMapping
    public ResponseEntity<?> updateProfile(@ModelAttribute UpdateProfileRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileService.updateProfile(request, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("PR104", "프로필 수정 성공", null));
    }

    @GetMapping("/details")
    public ResponseEntity<?> getProfileDetails(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        FindProfileResponse result = profileService.getProfileById(userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("PR105", "프로필 상세 조회 성공", result));
    }
}
