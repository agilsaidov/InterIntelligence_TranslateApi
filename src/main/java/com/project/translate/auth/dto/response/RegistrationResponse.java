package com.project.translate.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private String email;
    private String message;
}
