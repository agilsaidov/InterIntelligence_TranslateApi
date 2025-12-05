package com.project.translate.controller;

import com.deepl.api.TextResult;
import com.project.translate.dto.request.TranslationRequestDto;
import com.project.translate.dto.response.TranslationResponse;
import com.project.translate.service.TranslateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/translate")
@Slf4j
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateService translateService;

    @PostMapping
    public ResponseEntity<TranslationResponse> getTranslation(@AuthenticationPrincipal String userId,
                                                              @Valid @RequestBody TranslationRequestDto requestDto){

        log.info("Translating text from {} to {}", requestDto.getSourceLang(), requestDto.getTargetLang());

        TranslationResponse translationResponse = translateService.translate(userId, requestDto);

        return ResponseEntity.ok(translationResponse);
    }
}
