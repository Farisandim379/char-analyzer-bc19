package com.bootcamp19.helloworld.rest;

import com.bootcamp19.helloworld.dto.feign.QuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "quoteClient", url = "https://dummyjson.com/")
public interface QuoteClient {

    @GetMapping("/quotes/random")
    QuoteResponse getQuote();

    @GetMapping("/quotes/random/{length}")
    List<QuoteResponse> getQuotesBatch(@PathVariable("length") int length);
}
