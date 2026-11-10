package com.bootcamp19.helloworld.controller;

import com.bootcamp19.helloworld.dto.request.CharAnalysisRequest;
import com.bootcamp19.helloworld.dto.response.BaseResponse;
import com.bootcamp19.helloworld.dto.response.CharAnalysisResponse;
import com.bootcamp19.helloworld.entity.CharAnalysis;
import com.bootcamp19.helloworld.service.CharAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyzerController {

    private final CharAnalysisService charAnalysisService;

    @GetMapping
    public BaseResponse<List<CharAnalysisResponse>> cari(@RequestParam(required = false) String kalimat) {
        return BaseResponse.ok(charAnalysisService.cari(kalimat));
    }

    @PostMapping
    public BaseResponse<CharAnalysisResponse> analisis(
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @Valid @RequestBody CharAnalysisRequest request
    ) {
        log.info("X-Request-Id: {}, kalimat: {}", requestId, request != null ? request.getKalimat() : null);
        return BaseResponse.ok(charAnalysisService.analisis(request));
    }

    @GetMapping("/minimal")
    public BaseResponse<List<CharAnalysisResponse>> getMinimal(@RequestParam int min) {
        return BaseResponse.ok(charAnalysisService.getMinimal(min));
    }

    @GetMapping("/terbaru")
    public BaseResponse<List<CharAnalysisResponse>> getTerbaru() {
        return BaseResponse.ok(charAnalysisService.getTerbaru());
    }

    @GetMapping("/{id}")
    public BaseResponse<CharAnalysisResponse> getById(@PathVariable Long id) {
        CharAnalysisResponse hasil = charAnalysisService.getById(id);
        return BaseResponse.ok(hasil);
    }

    @PostMapping("/from-quote")
    public BaseResponse<CharAnalysisResponse> analisisDariQuote() {
        return BaseResponse.ok(charAnalysisService.analisisDariQuote());
    }

    @PostMapping("/from-quote/batch/{jumlah}")
    public BaseResponse<List<CharAnalysisResponse>> analisisBatchDariQuote(@PathVariable int jumlah) {
        return BaseResponse.ok(charAnalysisService.analisisBatchDariQuote(jumlah));
    }
}
