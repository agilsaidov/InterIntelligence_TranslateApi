package com.project.translate.controller;

import com.project.translate.dto.request.StarredTranslationRequest;
import com.project.translate.dto.response.StarredTranslationResponse;
import com.project.translate.service.TranslationHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/starred")
@RequiredArgsConstructor
public class StarredTranslationController {

    private final TranslationHistoryService translationHistoryService;

/*
    @PostMapping("/get")
    public List<StarredTranslationResponse>  getStarredTranslations(@RequestHeader("Authorization")  String token) {

        //translationHistoryService.getStarred();

    }
*/


}
