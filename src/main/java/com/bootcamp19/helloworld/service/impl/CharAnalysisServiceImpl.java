package com.bootcamp19.helloworld.service.impl;

import com.bootcamp19.helloworld.dto.feign.QuoteResponse;
import com.bootcamp19.helloworld.dto.request.CharAnalysisRequest;
import com.bootcamp19.helloworld.dto.response.CharAnalysisResponse;
import com.bootcamp19.helloworld.entity.CharAnalysis;
import com.bootcamp19.helloworld.exception.AnalysisConflictException;
import com.bootcamp19.helloworld.exception.AnalysisNotFoundException;
import com.bootcamp19.helloworld.exception.QuoteUnavailableException;
import com.bootcamp19.helloworld.repository.CharAnalysisRepository;
import com.bootcamp19.helloworld.rest.QuoteClient;
import com.bootcamp19.helloworld.service.CharAnalysisService;
import com.bootcamp19.helloworld.util.CharCounter;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharAnalysisServiceImpl implements CharAnalysisService {

    private final QuoteClient client;
    private final CharAnalysisRepository charAnalysisRepository;

    @Override
    public CharAnalysisResponse analisis(CharAnalysisRequest request) {
        String kalimat = request != null && request.getKalimat() != null ? request.getKalimat().trim() : "";
        return simpan(kalimat);
    }

    @Override
    public List<CharAnalysisResponse> cari(String kata) {
        List<CharAnalysis> semua;
        if (kata == null || kata.isBlank()) {
            semua = charAnalysisRepository.findAll();
        } else {
            semua = charAnalysisRepository.findByKalimatContainingIgnoreCase(kata);
        }

        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data : semua) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    @Override
    public CharAnalysisResponse getById(Long id) {
        CharAnalysis data = charAnalysisRepository.findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));
        return toResponse(data);
    }

    @Override
    public List<CharAnalysisResponse> getMinimal(int min) {
        List<CharAnalysis> list = charAnalysisRepository.findByMinimalHuruf(min);
        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data : list) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    @Override
    public List<CharAnalysisResponse> getTerbaru() {
        List<CharAnalysis> list = charAnalysisRepository.findTop5Terbaru();
        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data : list) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    @Override
    public CharAnalysisResponse analisisDariQuote() {
        QuoteResponse quote;
        try {
            quote = client.getQuote();
        } catch (FeignException feignException) {
            throw new QuoteUnavailableException("Service unavailable");
        }

        if (quote == null || quote.getQuote() == null) {
            throw new QuoteUnavailableException("Quote data is empty");
        }

        return simpan(quote.getQuote().trim());
    }

    private CharAnalysisResponse simpan(String kalimat) {
        if (charAnalysisRepository.existsByKalimatIgnoreCase(kalimat)) {
            throw new AnalysisConflictException(kalimat);
        }

        CharCounter counter = new CharCounter(kalimat);
        CharAnalysis entity = new CharAnalysis();
        entity.setKalimat(kalimat);
        entity.setTotalHuruf(counter.getTotalHuruf());
        entity.setCreatedAt(LocalDateTime.now());

        CharAnalysis saved = charAnalysisRepository.save(entity);
        return CharAnalysisResponse.from(saved, counter.toMap());
    }

    private CharAnalysisResponse toResponse(CharAnalysis data) {
        CharCounter counter = new CharCounter(data.getKalimat());
        return CharAnalysisResponse.from(data, counter.toMap());
    }
}
