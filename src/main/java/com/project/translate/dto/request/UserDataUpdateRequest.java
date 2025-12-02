package com.project.translate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserDataUpdateRequest {
    private String firstName;
    private String lastName;
    private String nativeLang;
    private String preferredLang;
}
