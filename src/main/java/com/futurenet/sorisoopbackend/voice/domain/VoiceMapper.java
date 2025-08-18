package com.futurenet.sorisoopbackend.voice.domain;

import com.futurenet.sorisoopbackend.voice.dto.response.VoiceResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoiceMapper {
    List<VoiceResponse> getVoiceList(Long profileId);
}
