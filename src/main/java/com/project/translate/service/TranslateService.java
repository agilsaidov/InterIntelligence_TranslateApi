package com.project.translate.service;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.project.translate.dto.request.TranslationRequestDto;
import com.project.translate.dto.response.TranslationResponse;
import com.project.translate.exception.TranslationException;
import com.project.translate.model.TranslationHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class TranslateService {

    private final DeepLClient deepLClient;
    private final TranslationHistoryService historyService;

    public TranslationResponse translate(TranslationRequestDto requestDto){

        log.info("Translation request by user {} (from {} to {})",
                requestDto.getUserId(),
                requestDto.getSourceLang(),
                requestDto.getTargetLang()
        );

        if(requestDto.getSourceLang() != null && requestDto.getSourceLang().isBlank()){
            requestDto.setSourceLang(null);
        }

        try {
            TextResult result = deepLClient.translateText(
                    requestDto.getText(),
                    requestDto.getSourceLang(),
                    requestDto.getTargetLang()
            );

            TranslationHistory savedTranslation =  historyService.saveTranslation(requestDto, result);

            log.debug("Translation is successful for user {} - translated characters: {}",
                    requestDto.getUserId(),
                    requestDto.getText().length()
            );

            return new TranslationResponse(
                    savedTranslation.getId(),
                    result.getText(),
                    result.getDetectedSourceLanguage()
            );

        }catch (DeepLException | InterruptedException e){
            log.error("Translate failed: {}", e.getMessage(), e);
            throw new TranslationException("Failed to translate text");
        }
    }
}
