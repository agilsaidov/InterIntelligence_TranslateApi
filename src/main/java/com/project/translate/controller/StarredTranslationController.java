package com.project.translate.controller;

import com.project.translate.model.TranslationHistory;
import com.project.translate.service.TranslationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/get")
    public ResponseEntity<Page<TranslationHistory>> getStarredTranslations(@RequestParam("userId") String userId,
                                                                           @RequestParam(defaultValue = "0") Integer page) {

        if(page < 0) page *= -1;

        return ResponseEntity.ok().
                body(translationHistoryService.getStarredTranslations(userId, PageRequest.of(page, 10)));
    }

}
