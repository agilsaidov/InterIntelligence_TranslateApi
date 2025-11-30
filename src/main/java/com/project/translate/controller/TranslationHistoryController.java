package com.project.translate.controller;

import com.project.translate.service.TranslationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/history")
@RequiredArgsConstructor
public class TranslationHistoryController {

    private final TranslationHistoryService translationHistoryService;


    @GetMapping
    public ResponseEntity<?> getTranslationHistory(@AuthenticationPrincipal String userId,
                                                   @RequestParam(defaultValue = "0") Integer page) {

        if(page < 0) page *=-1 ;

        return ResponseEntity.ok().
                body(translationHistoryService.getAllTranslations(
                        userId,
                        PageRequest.of(page, 10))
                );
    }


    @DeleteMapping
    public ResponseEntity<Void> removeTranslation(@AuthenticationPrincipal String userId,
                                                  @RequestParam("translationId") Long translationId) {

        translationHistoryService.removeTranslation(userId, translationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> removeAllTranslations(@AuthenticationPrincipal String userId) {

        translationHistoryService.clearHistory(userId);
        return ResponseEntity.noContent().build();
    }

}
