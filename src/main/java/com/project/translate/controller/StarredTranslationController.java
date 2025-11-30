package com.project.translate.controller;

import com.project.translate.model.TranslationHistory;
import com.project.translate.service.TranslationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/starred")
@RequiredArgsConstructor
public class StarredTranslationController {

    private final TranslationHistoryService translationHistoryService;


    @PostMapping
    public ResponseEntity<Void> addStarredTranslation(@AuthenticationPrincipal String userId,
                                                   @RequestParam("translationId") Long translationId) {

        translationHistoryService.addStarredTranslation(userId, translationId);
        return ResponseEntity.ok().build();
    }



    @GetMapping
    public ResponseEntity<Page<TranslationHistory>> getStarredTranslations(@AuthenticationPrincipal String userId,
                                                                           @RequestParam(defaultValue = "0") Integer page) {

        if(page < 0) page *= -1;

        return ResponseEntity.ok().
                body(translationHistoryService.getStarredTranslations(userId, PageRequest.of(page, 10)));
    }



    @DeleteMapping
    public ResponseEntity<Void> unstarTranslation(@AuthenticationPrincipal String userId,
                                                  @RequestParam("translationId") Long translationId) {

        translationHistoryService.unstarTranslation(userId, translationId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearStarredTranslations(@AuthenticationPrincipal String userId) {
        translationHistoryService.clearStarredTranslations(userId);
        return ResponseEntity.noContent().build();
    }

}
