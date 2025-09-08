package com.futurenet.sorisoopbackend.log.dto.response;

import com.futurenet.sorisoopbackend.log.dto.GetCategoryStatisticsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoryStatisticsResponse {
    private int duration;
    List<GetCategoryStatisticsDto> categoryStatisticsDtoList;
}
