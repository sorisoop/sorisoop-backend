package com.futurenet.sorisoopbackend.customfairytale.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomFairyTaleRepository {
    int saveSketch(String image);
}
