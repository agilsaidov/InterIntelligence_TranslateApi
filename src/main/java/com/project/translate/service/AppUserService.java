package com.project.translate.service;

import com.project.translate.dto.request.ChangePasswordRequest;
import com.project.translate.dto.request.UserDataUpdateRequest;
import com.project.translate.dto.response.UserResponse;
import com.project.translate.exception.NotFoundException;
import com.project.translate.exception.InvalidPasswordException;
import com.project.translate.model.AccountStatus;
import com.project.translate.model.AppUser;
import com.project.translate.repository.AppUserRepo;
import com.project.translate.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String,String> redisTemplate;

    private static final String USER_BLACKLIST_PREFIX = "blisted_user:";
    private static final Integer BLACKLIST_EXPIRATION = 60;

    public UserResponse getUserData(String userId) {

        log.info("Fetching details for user {}", userId);

        AppUser user = userRepo.findByPublicId(userId).orElseThrow(() ->
                new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );

        log.debug("Successfully fetched details for user {}", userId);

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

        log.info("Updating details for user {}", userId);

        AppUser user = userRepo.findByPublicId(userId).orElseThrow(
                () -> new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );

        log.debug("Successfully updated details for user {}", userId);

        user.setName(request.getFirstName());
        user.setSurname(request.getLastName());
        user.setNativeLang(request.getNativeLang());
        user.setPreferredLang(request.getPreferredLang());

        user.recordUpdate();

        AppUser savedUser = userRepo.save(user);
        return userMapper.toUserResponse(savedUser);

    }


    public void changePassword(String userId, ChangePasswordRequest request) {

        log.info("Changing password for user {}", userId);

        AppUser user = userRepo.findByPublicId(userId).orElseThrow(
                () -> new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );

        if(!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new InvalidPasswordException("NONMATCHED_PASSWORDS",
                    "New and confirmation passwords do not match"
            );
        }

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("INVALID_PASSWORD",
                    "The provided current password is invalid"
            );
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.recordUpdate();
        userRepo.save(user);

        log.info("Successfully changed password for user {}", userId);
    }


    public void softDeleteUser(String userId) {

        log.info("Deleting user {}", userId);

        AppUser user = userRepo.findByPublicId(userId).orElseThrow(
                () -> new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );

        user.setAccountStatus(AccountStatus.DELETED);
        user.recordUpdate();
        userRepo.save(user);

        redisTemplate.opsForValue().set(
                USER_BLACKLIST_PREFIX + userId,
                "DELETED USER",
                BLACKLIST_EXPIRATION,
                TimeUnit.MINUTES
        );

        log.info("Successfully deleted user {}", userId);
    }

}
