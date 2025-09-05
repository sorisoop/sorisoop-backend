package com.futurenet.sorisoopbackend.log.dto;

import com.futurenet.sorisoopbackend.log.domain.FairyTaleType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveReadLogDto {
    private Long profileId;
    private FairyTaleType fairyTaleType;
    private Long bookId;
    private int page;
}
