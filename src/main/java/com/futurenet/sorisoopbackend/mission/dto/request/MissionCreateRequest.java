package com.futurenet.sorisoopbackend.mission.dto.request;

import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissionCreateRequest {
    @NotNull
    private Long profileId;

    @NotNull
    private MissionType missionType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Long categoryId;
    private Integer bookCount;
    private Long fairyTaleId;
}
