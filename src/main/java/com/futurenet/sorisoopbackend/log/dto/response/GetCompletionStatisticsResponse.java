package com.futurenet.sorisoopbackend.log.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCompletionStatisticsResponse {
    private int duration;
    private int totalCount; // 읽기를 시도한 총 횟수
    private int readCount; // 완독한 횟수
}
