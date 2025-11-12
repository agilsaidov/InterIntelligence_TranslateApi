package com.project.translate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TranslationRequestDto {

    private String source;

    @NotBlank(message = "Target language is required")
    private String target;

    @NotBlank(message = "Text to translate is required")
    private String text;
}
