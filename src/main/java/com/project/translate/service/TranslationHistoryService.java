package com.project.translate.service;

import com.deepl.api.TextResult;
import com.project.translate.dto.request.TranslationRequestDto;
import com.project.translate.dto.response.StarredTranslationResponse;
import com.project.translate.dto.response.TranslationHistoryResponse;
import com.project.translate.exception.NotFoundException;
import com.project.translate.exception.StarredTranslationProcessException;
import com.project.translate.model.TranslationHistory;
import com.project.translate.repository.TranslationHistoryRepo;
import com.project.translate.utils.TranslationHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationHistoryService {

    private final TranslationHistoryRepo translationHistoryRepo;
    private final TranslationHistoryMapper translationHistoryMapper;

    @Transactional
    public TranslationHistory saveTranslation(String userId, TranslationRequestDto translationRequestDto, TextResult textResult) {

        log.info("Saving translation for user {}", userId);

        TranslationHistory translationHistory = TranslationHistory.builder()
                .userId(userId)
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


    public Page<TranslationHistoryResponse> getAllTranslations(String userId, Pageable pageable) {
        log.info("Fetching translation history for user {} (page: {}, size: {})",
                userId, pageable.getPageNumber(), pageable.getPageSize());

        Page<TranslationHistory> history = translationHistoryRepo.getTranslationHistoriesByUserId(userId, pageable);

        log.debug("Retrieved {} translations for user {}", history.getTotalElements(), userId);
        return history.map(translationHistoryMapper::toResponse);
    }


    @Transactional
    public void removeTranslation(String userId, Long translationId) {

        log.info("Removing translation {} from user {}", translationId, userId);

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

    @Transactional
    public void addStarredTranslation(String userId, Long translationId) {

        log.info("Star translation request {} by user {}", translationId, userId);

        int affected = translationHistoryRepo.starByUserIdAndTranslationId(userId, translationId);

        if(affected == 0){

            log.warn("Failed to star translation {} for user {} - not found or no permission",
                    translationId, userId);

            throw new StarredTranslationProcessException("STARRING_PROCESS_FAILURE",
                    "Could not star the translation. It may not exist, be deleted, or user don't have permission."
            );
        }

        log.debug("Translation {} starred successfully for user {}", translationId, userId);
    }


    public Page<TranslationHistory> getStarredTranslations(String userId, Pageable pageable) {
        log.info("Starred translations fetched by user {}", userId);

        return translationHistoryRepo.getStarredTranslationsByUserId(userId, pageable);
    }


    public StarredTranslationResponse getStarredTranslation(String userId, Long translationId) {
        log.info("Starred translation request {} by user {}", translationId, userId);

        TranslationHistory response = translationHistoryRepo.getStarredTranslationByUserIdAndTranslationId(userId, translationId);

        if(response == null){
            log.warn("Starred translation {} not found for user {}", translationId, userId);

            throw new NotFoundException("STARRED_TRANSLATION_NOT_FOUND",
                    "Starred translation " + translationId + " not found for user " + userId
            );

        }

        return StarredTranslationResponse.builder()
                .translationId(response.getId())
                .sourceText(response.getSourceText())
                .translatedText(response.getTranslatedText())
                .sourceLang(response.getSourceLang())
                .targetLang(response.getTargetLang())
                .starredAt(response.getStarredAt())
                .build();
    }


    @Transactional
    public void unstarTranslation(String userId, Long translationId) {

        log.info("Unstar translation request {} by user {}", translationId, userId);

        int affected = translationHistoryRepo.unstarTranslationByUserIdAndTranslationId(userId, translationId);

        if(affected == 0){
            log.warn("Failed to unstar translation {} for user {} - not found or no permission",
                    translationId, userId);

            throw new StarredTranslationProcessException("STARRING_PROCESS_FAILURE",
                    "Could not unstar the translation. It may not exist, be already deleted, or you don't have permission."
            );
        }
        log.debug("Translation {} has been unstarred successfully for user {}", translationId, userId);
    }


    @Transactional
    public void clearStarredTranslations(String userId) {

        log.info("Clear all starred translations request by user {}",  userId);

       int affected = translationHistoryRepo.clearStarredTranslationsByUserId(userId);
        if(affected == 0){
            log.debug("No Starred translation to clear for user {}", userId);
        }else{
            log.debug("Cleared {} starred translation(s) for user {}", affected, userId);
        }
    }

}
