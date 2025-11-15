package com.project.translate.service;

import com.project.translate.dto.request.StarredTranslationRequest;
import com.project.translate.model.StarredTranslation;
import com.project.translate.repository.StaredTranslationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaredTranslationService {

    private final StaredTranslationRepo repo;

    public void add(StarredTranslationRequest request) {
        StarredTranslation staredTranslation = new StarredTranslation();

        staredTranslation.setUserId(request.getUserId());
        staredTranslation.setSourceText(request.getSourceText());
        staredTranslation.setTranslatedText(request.getTranslatedText());
        staredTranslation.setSourceLang(request.getSourceLang());
        staredTranslation.setTargetLang(request.getTargetLang());

        repo.save(staredTranslation);
    }
}
