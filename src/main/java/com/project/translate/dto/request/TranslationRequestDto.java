package com.project.translate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TranslationRequestDto {

    @NotNull(message = "userId is required")
    private String userId;

    private String sourceLang;

    @NotBlank(message = "Target language is required")
    @Size(min=2, max=6, message = "Target language is not valid")
    private String targetLang;

    @NotBlank(message = "Text to translate is required")
    private String text;
}
