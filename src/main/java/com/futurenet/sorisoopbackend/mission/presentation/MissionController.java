package com.futurenet.sorisoopbackend.mission.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.mission.application.MissionService;
import com.futurenet.sorisoopbackend.mission.dto.request.SaveMissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<?> assignMission(@RequestBody SaveMissionRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        missionService.saveMission(request, userPrincipal.getProfileId(), userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>("MI100", "미션 생성 성공", null));
    }
}
