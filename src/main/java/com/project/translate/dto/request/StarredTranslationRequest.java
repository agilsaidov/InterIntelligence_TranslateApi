package com.project.translate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StarredTranslationRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotBlank(message = "sourceText is required")
    private String sourceText;

    @NotBlank(message = "translatedText is required")
    private String translatedText;

    @NotBlank(message = "sourceLang is required")
    @Size(min = 2, max = 6, message = "source language is not valid")
    private String sourceLang;

    @NotBlank(message = "targetLang is required")
    @Size(min = 2, max = 6, message = "target language is not valid")
    private String targetLang;
}
