package com.project.translate.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StarredTranslationResponse {
    private Long translationId;
    private String sourceText;
    private String translatedText;
    private String sourceLang;
    private String targetLang;
    private LocalDateTime starredAt;
}
