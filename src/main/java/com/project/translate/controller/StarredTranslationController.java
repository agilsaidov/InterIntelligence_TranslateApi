package com.project.translate.controller;

import com.project.translate.dto.request.StarredTranslationRequest;
import com.project.translate.service.StaredTranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/starred")
@RequiredArgsConstructor
public class StarredTranslationController {

    private final StaredTranslationService service;

    @PostMapping("/add")
    public ResponseEntity<String> addStaredTranslation(@Valid @RequestBody StarredTranslationRequest request) {
        service.add(request);
        return ResponseEntity.ok("Successfully added");
    }
}
