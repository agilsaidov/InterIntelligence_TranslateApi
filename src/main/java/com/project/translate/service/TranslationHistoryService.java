package com.project.translate.service;

import com.deepl.api.TextResult;
import com.project.translate.dto.request.TranslationRequestDto;
import com.project.translate.exception.NotFoundException;
import com.project.translate.exception.StarredTranslationProcessException;
import com.project.translate.model.TranslationHistory;
import com.project.translate.repository.TranslationHistoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationHistoryService {

    private final TranslationHistoryRepo translationHistoryRepo;

    @Transactional
    public TranslationHistory saveTranslation(TranslationRequestDto translationRequestDto, TextResult textResult) {

        TranslationHistory translationHistory = TranslationHistory.builder()
                .userId(translationRequestDto.getUserId())
                .sourceLang(textResult.getDetectedSourceLanguage())
                .targetLang(translationRequestDto.getTargetLang())
                .translatedText(textResult.getText())
                .sourceText(translationRequestDto.getText())
                .starred(false)
                .deleted(false)
                .build();

        TranslationHistory saved =  translationHistoryRepo.save(translationHistory);

        log.debug("Saved translation {} for user {}", saved.getId(), saved.getUserId());

        return saved;
    }


    public List<TranslationHistory> getAllTranslations(String userId, Pageable pageable) {
        List<TranslationHistory> history = translationHistoryRepo.getTranslationHistoriesByUserId(userId, pageable);
        log.info("History fetched by user {}", userId);
        return history;
    }


    @Transactional
    public void removeTranslation(String userId, Long translationId) {

        int affectedRows = translationHistoryRepo.softDeleteByIdAndUserId(translationId, userId);

        if(affectedRows == 0){
            log.warn("Translation {} not found for user {}", translationId, userId);

            throw new NotFoundException("TRANSLATION_NOT_FOUND",
                    "Translation not found in history");
        }

        log.info("Translation {} has been removed for user {}", translationId, userId);

    }

    @Transactional
    public void clearHistory(String userId) {
        int affected = translationHistoryRepo.softDeleteHistoryByUserId(userId);
        log.info("History has been cleared for user {} and {} rows affected", userId, affected);
    }



    //Starred Translation Methods

    public void addStarredTranslation(String userId, Long translationId) {

        int affected = translationHistoryRepo.starByUserIdAndTranslationId(userId, translationId);
        if(affected == 0){
            throw new StarredTranslationProcessException("STARRING_PROCESS_FAILURE",
                    "Could not star the translation. It may not exist, be deleted, or you don't have permission."
            );
        }

        log.info("Translation {} has been starred for user {}", translationId, userId);
    }


    public Page<TranslationHistory> getStarredTranslations(String userId, Pageable pageable) {

        return translationHistoryRepo.getStarredTranslationsByUserId(userId, pageable);
    }

}
