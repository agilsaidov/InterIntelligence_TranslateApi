package com.project.translate.controller;

import com.project.translate.dto.request.StarredTranslationRequest;
import com.project.translate.dto.response.StarredTranslationResponse;
import com.project.translate.service.StarredTranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/starred")
@RequiredArgsConstructor
public class StarredTranslationController {

    private final StarredTranslationService service;

    @GetMapping("/getAll")
    public ResponseEntity<List<StarredTranslationResponse>> getStarredTranslation(@RequestParam String userId){
        return ResponseEntity.ok().body(service.getStarredTranslations(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStaredTranslation(@Valid @RequestBody StarredTranslationRequest request) {
        service.starTranslation(request);
        return ResponseEntity.ok("Successfully added");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeStaredTranslation(@Valid @RequestBody StarredTranslationRequest request) {
        service.unstarTranslation(request);
        return ResponseEntity.ok("Successfully removed");
    }
}
