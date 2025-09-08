package com.futurenet.sorisoopbackend.global.scheduler;

import com.futurenet.sorisoopbackend.mission.application.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MissionStatusScheduler {

    private final MissionService missionService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void updateMissionsToOngoing() {
        missionService.updateMissionStatusOngoing();
    }

    @Scheduled(cron = "0 10 0 * * *", zone = "Asia/Seoul")
    public void updateMissionStatusFailed() {
        missionService.updateMissionStatusFailed();
    }
}
