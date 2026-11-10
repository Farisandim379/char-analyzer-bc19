package com.bootcamp19.helloworld.rest;

import com.bootcamp19.helloworld.dto.feign.QuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "quoteClient", url = "https://dummyjson.com/")
public interface QuoteClient {

    @GetMapping("/quotes/random")
    QuoteResponse getQuote();
}
