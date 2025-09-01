package com.futurenet.sorisoopbackend.tts.domain;

import com.futurenet.sorisoopbackend.tts.dto.TtsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TtsRepository {
    String getVoiceUrlById(Long voiceId);
    List<TtsDto> getFairyTaleList(Long fairyTaleId);
}
