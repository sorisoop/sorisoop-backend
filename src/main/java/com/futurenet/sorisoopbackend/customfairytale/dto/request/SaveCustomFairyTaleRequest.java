package com.futurenet.sorisoopbackend.customfairytale.dto.request;

import com.futurenet.sorisoopbackend.customfairytale.dto.MakeCustomFairyTaleDto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveCustomFairyTaleRequest {
    private Long id;
    private Long profileId;
    private Long categoryId;
    private String title;
    private String thumbnailImage;
    private int pageCount;

    public static SaveCustomFairyTaleRequest of(MakeCustomFairyTaleDto dto, Long profileId, String thumbnailImage) {
        return SaveCustomFairyTaleRequest.builder()
                .profileId(profileId)
                .categoryId(dto.getCategoryId())
                .title(dto.getTitle())
                .thumbnailImage(thumbnailImage)
                .pageCount(dto.getPages().size())
                .build();
    }
}
