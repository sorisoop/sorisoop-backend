package com.futurenet.sorisoopbackend.customfairytale.application;

import com.futurenet.sorisoopbackend.customfairytale.dto.request.MakeCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.MakeCustomFairyTaleConceptResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MakeFairyTaleService {
    MakeCustomFairyTaleConceptResponse makeSynopsis(MultipartFile image, Long profileId);
    List<MakeCustomFairyTaleResponse> createCustomFairyTale(MakeCustomFairyTaleRequest request);
}
