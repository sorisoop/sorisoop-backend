package com.futurenet.sorisoopbackend.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadBookMissionInfoDto {
    private Long fairyTaleId;
    private boolean isRead;
}
