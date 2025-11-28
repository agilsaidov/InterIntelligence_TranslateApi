package com.project.translate.auth.service;

import com.project.translate.auth.dto.request.RegistrationRequest;
import com.project.translate.auth.dto.response.RegistrationResponse;
import com.project.translate.exception.UserException;
import com.project.translate.model.AppUser;
import com.project.translate.repository.UserRepo;
import com.project.translate.utils.UserIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserIdGenerator idGenerator;

    public RegistrationResponse registerUser(RegistrationRequest request) {

        if(userRepo.existsAppUserByEmail(request.getEmail())){
            throw new UserException(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS", "User with given email already exists");
        }

        if(userRepo.existsAppUserByUsername(request.getUsername())){
            throw new UserException(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS", "User with given username already exists");
        }

        AppUser savedUser = userRepo.save(AppUser.builder()
                        .publicId(idGenerator.generateUserId())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .username(request.getUsername())
                        .build()
        );
        System.out.println(savedUser);

        return RegistrationResponse.builder()
                .email(savedUser.getEmail())
                .message("Registered Successfully")
                .build();
    }
}
