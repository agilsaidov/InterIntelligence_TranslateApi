package com.project.translate.security;

import com.project.translate.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtService {

    @Value("${JWT_SECRET}")
    private String secret;

    public String generateToken(AppUser user) {
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getPublicId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .and()
                .signWith(getSecretKey())
                .compact();

    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        }catch(JwtException | IllegalArgumentException e){
            return  false;
        }
    }


    public String getIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }



    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
