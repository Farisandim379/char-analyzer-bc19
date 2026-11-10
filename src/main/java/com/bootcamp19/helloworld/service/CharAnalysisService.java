package com.bootcamp19.helloworld.service;

import com.bootcamp19.helloworld.dto.request.CharAnalysisRequest;
import com.bootcamp19.helloworld.dto.response.CharAnalysisResponse;

import java.util.List;

public interface CharAnalysisService {
    List<CharAnalysisResponse> cari(String kata);

    CharAnalysisResponse getById(Long id);

    CharAnalysisResponse analisis(CharAnalysisRequest request);

    List<CharAnalysisResponse> getMinimal(int min);

    List<CharAnalysisResponse> getTerbaru();

    CharAnalysisResponse analisisDariQuote();

    List<CharAnalysisResponse> analisisBatchDariQuote(int jumlah);
}
