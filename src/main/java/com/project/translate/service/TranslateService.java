package com.project.translate.service;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TranslateService {

    @Value("${DEEPL_API_KEY}")
    private String API_KEY;

    public String translate(String sourceLang, String targetLang, String text){
        try {
            DeepLClient deepLClient = new DeepLClient(API_KEY);
            TextResult result = deepLClient.translateText(text, sourceLang, targetLang);
            return result.getText();

        }catch (DeepLException | InterruptedException e){
            return e.getMessage();
        }
    }
}
