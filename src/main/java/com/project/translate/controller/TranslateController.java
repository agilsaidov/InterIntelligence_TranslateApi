package com.project.translate.controller;

import com.project.translate.dto.TranslationRequestDto;
import com.project.translate.service.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateService translateService;

    @PostMapping("/translate")
    public String getResult(@RequestBody TranslationRequestDto requestDto){
        return translateService.translate(requestDto.getSource(),requestDto.getTarget(),requestDto.getText());
    }
}
