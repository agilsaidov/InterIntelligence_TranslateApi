package com.project.translate.dto;

import lombok.Data;

@Data
public class TranslationRequestDto {
    private String source;
    private String target;
    private String text;
}
