package com.project.translate.config;

import com.deepl.api.DeepLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeepLConfig {

    @Value("${DEEPL_API_KEY}")
    private String apiKey;

    @Bean
    public DeepLClient deepLClient() {
        return new DeepLClient(apiKey);
    }
}
