package com.project.translate.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UserIdGenerator {
    private static final String chars = "ABCDEF123GHIJKLMN567OPQRST04UVWXYZ89";
    private static final SecureRandom random = new SecureRandom();
    private static final int ID_LENGTH = 8;

    public String generateUserId() {
        StringBuilder id = new StringBuilder(ID_LENGTH);

        for(int i = 0; i<ID_LENGTH; i++){
            id.append(chars.charAt(random.nextInt(chars.length())));
        }
        return id.toString();
    }
}
