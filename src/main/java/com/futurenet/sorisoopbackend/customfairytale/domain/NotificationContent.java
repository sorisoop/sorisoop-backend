package com.futurenet.sorisoopbackend.customfairytale.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationContent {
    MAKE_FAIRY_TALE_COMPLETE("동화 생성이 완료되었습니다.");

    private final String message;
}
