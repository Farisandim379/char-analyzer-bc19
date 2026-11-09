package com.bootcamp19.helloworld.controller;

import com.bootcamp19.helloworld.dto.request.CharAnalysisRequest;
import com.bootcamp19.helloworld.dto.response.BaseResponse;
import com.bootcamp19.helloworld.dto.response.CharAnalysisResponse;
import com.bootcamp19.helloworld.service.CharAnalysisService;
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
    public BaseResponse<CharAnalysisResponse> simpan(
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @RequestBody CharAnalysisRequest request
    ) {
        log.info("X-Request-Id: {}, kalimat: {}", requestId, request != null ? request.getKalimat() : null);
        return BaseResponse.ok(charAnalysisService.simpan(request));
    }
}
