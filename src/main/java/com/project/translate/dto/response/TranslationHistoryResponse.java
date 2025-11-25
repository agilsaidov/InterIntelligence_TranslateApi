package com.project.translate.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TranslationHistoryResponse {
    private Long translationId;
    private String sourceLang;
    private String targetLang;
    private String sourceText;
    private String translatedText;
    private LocalDate translatedAt;
}
