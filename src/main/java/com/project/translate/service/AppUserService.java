package com.project.translate.service;

import com.project.translate.dto.request.ChangePasswordRequest;
import com.project.translate.dto.request.UserDataUpdateRequest;
import com.project.translate.dto.response.UserResponse;
import com.project.translate.exception.NotFoundException;
import com.project.translate.exception.InvalidPasswordException;
import com.project.translate.model.AppUser;
import com.project.translate.repository.AppUserRepo;
import com.project.translate.security.JwtService;
import com.project.translate.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserData(String userId) {

        AppUser user = userRepo.findByPublicId(userId).orElseThrow(() ->
                new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );

        return UserResponse.builder()
                .userId(user.getPublicId())
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .nativeLang(user.getNativeLang())
                .preferredLang(user.getPreferredLang())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .profilePicture(user.getPpUrl())
                .build();
    }


    public UserResponse updateUserData(String userId, UserDataUpdateRequest request) {

        AppUser user = userRepo.findByPublicId(userId).orElseThrow(
                () -> new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );

        user.setName(request.getFirstName());
        user.setSurname(request.getLastName());
        user.setNativeLang(request.getNativeLang());
        user.setPreferredLang(request.getPreferredLang());

        user.recordUpdate();

        AppUser savedUser = userRepo.save(user);
        return userMapper.toUserResponse(savedUser);

    }


    public void changePassword(String userId, ChangePasswordRequest request) {
        AppUser user = userRepo.findByPublicId(userId).orElseThrow(
                () -> new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("INVALID_PASSWORD",
                    "The provided current password is invalid"
            );
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.recordUpdate();
        userRepo.save(user);
    }
}
