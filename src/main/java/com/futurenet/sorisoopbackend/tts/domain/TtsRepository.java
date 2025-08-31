package com.futurenet.sorisoopbackend.tts.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TtsRepository {
    String getVoiceUrlById(Long voiceId);
}
