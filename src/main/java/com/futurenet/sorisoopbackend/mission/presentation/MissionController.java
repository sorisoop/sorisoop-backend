package com.futurenet.sorisoopbackend.mission.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.mission.aop.ValidateMissionProfile;
import com.futurenet.sorisoopbackend.mission.application.MissionService;
import com.futurenet.sorisoopbackend.mission.dto.request.MissionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;

    @PostMapping
    @ValidateMissionProfile
    public ResponseEntity<?> createMission(@RequestBody MissionCreateRequest request) {
        missionService.createMission(request);
        return ResponseEntity.ok(new ApiResponse<>("MI100", "미션 생성에 성공했습니다.", null));
    }

}
