package com.futurenet.sorisoopbackend.voice.domain;

import com.futurenet.sorisoopbackend.voice.dto.request.AddVoiceRequest;
import com.futurenet.sorisoopbackend.voice.dto.request.UpdateVoiceInfoRequest;
import com.futurenet.sorisoopbackend.voice.dto.response.GetVoiceResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoiceRepository {
    List<GetVoiceResponse> getVoiceList(Long profileId);
    int saveVoice(AddVoiceRequest request);
    int updateVoiceInfo(Long voiceId, UpdateVoiceInfoRequest request);
}
