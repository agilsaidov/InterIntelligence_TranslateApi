package com.project.translate.service;

import com.deepl.api.TextResult;
import com.project.translate.dto.request.TranslationRequestDto;
import com.project.translate.model.TranslationHistory;
import com.project.translate.repository.TranslationHistoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationHistoryService {

    private final TranslationHistoryRepo translationHistoryRepo;

    public TranslationHistory saveTranslation(TranslationRequestDto translationRequestDto, TextResult textResult) {

        TranslationHistory translationHistory = new TranslationHistory();

        translationHistory.setUserId(translationRequestDto.getUserId());
        translationHistory.setSourceLang(textResult.getDetectedSourceLanguage());
        translationHistory.setTargetLang(translationRequestDto.getTargetLang());
        translationHistory.setTranslatedText(textResult.getText());
        translationHistory.setSourceText(translationRequestDto.getText());

        return translationHistoryRepo.save(translationHistory);
    }

}
