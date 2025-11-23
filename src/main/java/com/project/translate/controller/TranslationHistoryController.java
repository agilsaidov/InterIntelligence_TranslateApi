package com.project.translate.controller;

import com.project.translate.service.TranslationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/history")
@RequiredArgsConstructor
public class TranslationHistoryController {

    private final TranslationHistoryService translationHistoryService;

    @DeleteMapping("/remove/{translationId}")
    public ResponseEntity<Void> remove(@RequestHeader("Authorization") String token,
                                          @PathVariable Long translationId) {

        //After security implementation userId will be sent instead of token
        translationHistoryService.removeTranslation(token, translationId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/clear")
    public ResponseEntity<Void> removeAll(@RequestHeader("Authorization") String token) {

        //After security implementation userId will be sent instead of token
        translationHistoryService.clearHistory(token);
        return ResponseEntity.noContent().build();
    }

}
