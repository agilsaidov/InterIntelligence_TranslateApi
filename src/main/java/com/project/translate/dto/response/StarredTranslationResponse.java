package com.project.translate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarredTranslationResponse {
    private Long translationId;
    private String sourceText;
    private String translatedText;
    private String sourceLang;
    private String targetLang;
    private LocalDateTime starredAt;
}
