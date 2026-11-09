package com.bootcamp19.helloworld.service;

import com.bootcamp19.helloworld.dto.request.CharAnalysisRequest;
import com.bootcamp19.helloworld.dto.response.CharAnalysisResponse;

import java.util.List;
import java.util.Optional;

public interface CharAnalysisService {
    List<CharAnalysisResponse> cari(String kata);

    CharAnalysisResponse simpan(CharAnalysisRequest request);

    Optional<CharAnalysisResponse> getById(Long id);
}
