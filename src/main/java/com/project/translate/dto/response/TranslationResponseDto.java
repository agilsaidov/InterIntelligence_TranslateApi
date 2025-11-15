package com.project.translate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslationResponseDto {
    private String translatedText;
    private String detectedSourceLanguage;
}
