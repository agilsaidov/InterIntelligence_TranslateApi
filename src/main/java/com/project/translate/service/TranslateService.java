package com.project.translate.service;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.project.translate.exception.TranslationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class TranslateService {

    private final DeepLClient deepLClient;

    public TextResult translate(String sourceLang, String targetLang, String text){
        if(sourceLang != null && sourceLang.isBlank()){
            sourceLang = null;
        }

        try {
            return deepLClient.translateText(text, sourceLang, targetLang);

        }catch (DeepLException | InterruptedException e){
            log.error("Translate failed: {}", e.getMessage(), e);
            throw new TranslationException("Failed to translate text");
        }
    }
}
