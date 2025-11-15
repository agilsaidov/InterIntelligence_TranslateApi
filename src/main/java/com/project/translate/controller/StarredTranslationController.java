package com.project.translate.controller;

import com.project.translate.dto.request.StarredTranslationRequest;
import com.project.translate.service.StaredTranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/starred")
@RequiredArgsConstructor
public class StarredTranslationController {

    private final StaredTranslationService service;

    @PostMapping("/add")
    public ResponseEntity<String> addStaredTranslation(@Valid @RequestBody StarredTranslationRequest request) {
        service.starTranslation(request);
        return ResponseEntity.ok("Successfully added");
    }

/*    @DeleteMapping("/remove")
    public ResponseEntity<String> removeStaredTranslation(@Valid @RequestBody StarredTranslationRequest request) {

    }*/
}
