package com.bootcamp19.helloworld.controller;

import com.bootcamp19.helloworld.dto.response.BaseResponse;
import com.bootcamp19.helloworld.dto.response.CharAnalysisResponse;
import com.bootcamp19.helloworld.service.CharAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnalyzerController {

    private final CharAnalysisService charAnalysisService;

    @GetMapping("/find")
    public BaseResponse<List<CharAnalysisResponse>> cari(@RequestParam(required = false) String kalimat) {
        return BaseResponse.ok(charAnalysisService.cari(kalimat));
    }
}
