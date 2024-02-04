package com.example.wordstraining.proxy;

import com.example.wordstraining.DTO.TranslateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "translate",
        url = "${translate-api.url}")
public interface TranslateProxy {

    @PostMapping("/translate")
    String translate(@RequestBody TranslateDTO translateDTO);

}
