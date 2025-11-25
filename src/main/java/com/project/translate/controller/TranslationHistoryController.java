package com.project.translate.controller;

import com.project.translate.service.TranslationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/history")
@RequiredArgsConstructor
public class TranslationHistoryController {

    private final TranslationHistoryService translationHistoryService;


    @GetMapping
    public ResponseEntity<?> getTranslationHistory(@RequestParam("userId") String userId,
                                                   @RequestParam(defaultValue = "0") Integer page) {

        if(page < 0) page *=-1 ;

        return ResponseEntity.ok().
                body(translationHistoryService.getAllTranslations(
                        userId,
                        PageRequest.of(page, 10))
                );
    }


    @DeleteMapping
    public ResponseEntity<Void> removeTranslation(@RequestHeader("Authorization") String token,
                                                  @RequestParam("translationId") Long translationId) {

        //After security implementation userId will be sent instead of token
        translationHistoryService.removeTranslation(token, translationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> removeAllTranslations(@RequestHeader("Authorization") String token
    ) {

        //After security implementation userId will be sent instead of token
        translationHistoryService.clearHistory(token);
        return ResponseEntity.noContent().build();
    }

}
