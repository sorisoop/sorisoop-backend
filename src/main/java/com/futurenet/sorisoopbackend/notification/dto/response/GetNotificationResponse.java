package com.futurenet.sorisoopbackend.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetNotificationResponse {
    private Long id;
    private Long customFairyTaleId;
    private String content;
    private String thumbnailImage;
    private String title;
    private LocalDateTime createdAt;
    private String isRead;
    private String customFairyTaleIsDelete;
}
