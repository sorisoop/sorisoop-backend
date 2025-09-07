package com.futurenet.sorisoopbackend.log.dto.request;

import com.futurenet.sorisoopbackend.log.domain.FairyTaleType;
import com.futurenet.sorisoopbackend.log.domain.LogType;
import com.futurenet.sorisoopbackend.log.dto.SaveReadLogDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveReadLogRequest {
    private FairyTaleType fairyTaleType;
    private Long bookId;
    private int page;
    private LogType logType;

    public SaveReadLogDto toDto(Long profileId) {
        return SaveReadLogDto.builder()
                .bookId(bookId)
                .fairyTaleType(fairyTaleType)
                .page(page)
                .profileId(profileId)
                .logType(logType)
                .build();
    }
}
