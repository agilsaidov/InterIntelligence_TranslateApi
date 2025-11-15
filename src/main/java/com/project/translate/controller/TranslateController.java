package com.project.translate.controller;

import com.deepl.api.TextResult;
import com.project.translate.dto.request.TranslationRequestDto;
import com.project.translate.dto.response.TranslationResponseDto;
import com.project.translate.service.TranslateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/translate")
@Slf4j
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateService translateService;

    @PostMapping
    public ResponseEntity<TranslationResponseDto> getTranslation(@Valid @RequestBody TranslationRequestDto requestDto){

        log.info("Translating text from {} to {}", requestDto.getSource(), requestDto.getTarget());

        TextResult translatedText = translateService.translate(requestDto.getSource(), requestDto.getTarget(), requestDto.getText());

        return ResponseEntity.ok(new TranslationResponseDto(translatedText.getText(), translatedText.getDetectedSourceLanguage()));
    }
}
