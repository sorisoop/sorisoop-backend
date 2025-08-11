package com.futurenet.sorisoopbackend.customfairytale.application;

import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MakeFairyTaleService {
    List<MakeCustomFairyTaleResponse> createCustomFairyTale(MultipartFile image);
}
