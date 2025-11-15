package com.project.translate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StarredTranslationRequest {

    @NotNull(message = "translationId is required")
    private Long translationId;

    @NotBlank(message = "userId is required")
    @Size(min = 8,max = 10, message = "UserId is not valid")
    private String userId;
}
