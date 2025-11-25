package com.project.translate.controller;

import com.project.translate.dto.request.StarredTranslationRequest;
import com.project.translate.service.TranslationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/starred")
@RequiredArgsConstructor
public class StarredTranslationController {

    private final TranslationHistoryService translationHistoryService;

    @PostMapping("/add")
    public ResponseEntity<Void> addStarredTranslation(@RequestParam("userId") String userId,
                                                   @RequestParam("translationId") Long translationId) {

        translationHistoryService.addStarredTranslation(userId, translationId);
        return ResponseEntity.ok().build();
    }

}
