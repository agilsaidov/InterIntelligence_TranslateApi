package com.project.translate.utils;

import java.security.SecureRandom;

public class IdGenerator {
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final int ID_LENGTH = 8;

    public static String generateUserId() {
        StringBuilder id = new StringBuilder(ID_LENGTH);

        for(int i = 0; i<ID_LENGTH; i++){
            id.append(chars.charAt(random.nextInt(chars.length())));
        }
        return id.toString();
    }
}
