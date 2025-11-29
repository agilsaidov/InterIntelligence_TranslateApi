package com.project.translate.auth.service;

import com.project.translate.auth.dto.request.LoginRequest;
import com.project.translate.auth.dto.request.RegistrationRequest;
import com.project.translate.auth.dto.response.LoginResponse;
import com.project.translate.auth.dto.response.RegistrationResponse;
import com.project.translate.exception.AuthException;
import com.project.translate.model.AppUser;
import com.project.translate.repository.UserRepo;
import com.project.translate.security.JwtService;
import com.project.translate.utils.UserIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserIdGenerator idGenerator;
    private final JwtService jwtService;

    @Transactional
    public RegistrationResponse registerUser(RegistrationRequest request) {

        if(userRepo.existsAppUserByEmail(request.getEmail())){
            throw new AuthException(HttpStatus.CONFLICT, "EMAIL_ALREADY_EXISTS", "User with given email already exists");
        }

        if(userRepo.existsAppUserByUsername(request.getUsername())){
            throw new AuthException(HttpStatus.CONFLICT, "USERNAME_ALREADY_EXISTS", "User with given username already exists");
        }

        AppUser savedUser = userRepo.save(AppUser.builder()
                        .publicId(idGenerator.generateUserId())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .username(request.getUsername())
                        .build()
        );

        log.info("User registered successfully with email: {}", savedUser.getEmail());

        return RegistrationResponse.builder()
                .email(savedUser.getEmail())
                .message("Registered Successfully")
                .build();
    }


    @Transactional
    public LoginResponse loginUser(LoginRequest request) {

        AppUser user = userRepo.findAppUserByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException(
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_CREDENTIALS",
                        "Given credential(s) are not valid")
                );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AuthException(
                    HttpStatus.UNAUTHORIZED,
                    "INVALID_CREDENTIALS",
                    "Given credential(s) are not valid"
            );
        }

        user.setLoginCount(user.getLoginCount()+1);
        user.setLastLogin(LocalDateTime.now());

        userRepo.save(user);
        log.info("Login Successfully with email: {}", user.getEmail());

        return LoginResponse.builder()
                .token(jwtService.generateToken(user))
                .email(user.getEmail())
                .username(user.getUsername())
                .publicId(user.getPublicId())
                .profilePicture(user.getPpUrl())
                .build();
    }
}
