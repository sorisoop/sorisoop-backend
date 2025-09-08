package com.futurenet.sorisoopbackend.mission.domain;

import com.futurenet.sorisoopbackend.mission.dto.GetMissionDto;
import com.futurenet.sorisoopbackend.mission.dto.SaveMissionDto;
import com.futurenet.sorisoopbackend.mission.dto.response.GetAssignedMissionResponse;
import com.futurenet.sorisoopbackend.mission.dto.response.GetGivenMissionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MissionRepository {
    int saveMission(@Param("request") SaveMissionDto request);
    List<GetGivenMissionResponse> getAllGivenMission(@Param("childProfileId") Long childProfileId, @Param("profileId") Long profileId);
    List<GetAssignedMissionResponse> getAllAssignedMission(Long profileId);
    GetMissionDto getMissionByMissionId(Long missionId);
    int deleteMissionByMissionIdAndProfileId(Long missionId, Long profileId);
    void updateMissionStatus(Long missionId, MissionStatus missionStatus);
    void updateMissionStatusOngoing(LocalDate today);
    List<GetMissionDto> findNotCompletedMissionBeforeEndDate(LocalDate today);
}
