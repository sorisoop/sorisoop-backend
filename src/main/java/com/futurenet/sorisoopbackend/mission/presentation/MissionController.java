package com.futurenet.sorisoopbackend.mission.presentation;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.mission.application.MissionService;
import com.futurenet.sorisoopbackend.mission.dto.request.SaveMissionRequest;
import com.futurenet.sorisoopbackend.mission.dto.response.GetAssignedMissionResponse;
import com.futurenet.sorisoopbackend.mission.dto.response.GetGivenMissionResponse;
import com.futurenet.sorisoopbackend.mission.dto.response.GetMissionDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 부모용
     */
    @GetMapping("/{childProfileId}/given")
    public ResponseEntity<?> getGivenMissions(@PathVariable Long childProfileId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<GetGivenMissionResponse> result = missionService.getAllGivenMission(childProfileId, userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("MI101", "미션 조회 성공", result));
    }

    /**
     * 아이용
     * */
    @GetMapping("/assigned")
    public ResponseEntity<?> getAssignedMissions(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<GetAssignedMissionResponse> result = missionService.getAllAssignedMission(userPrincipal.getProfileId());
        return ResponseEntity.ok(new ApiResponse<>("MI102", "미션 조회 성공", result));
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<?> getMissionContent(@PathVariable Long missionId) {
        GetMissionDetailResponse result = missionService.getMissionDetail(missionId);
        return ResponseEntity.ok(new ApiResponse<>("MI103", "미션 상세 조회 성공", result));
    }
}
