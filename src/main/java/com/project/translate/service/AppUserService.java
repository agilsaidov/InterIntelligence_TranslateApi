package com.project.translate.service;

import com.project.translate.exception.NotFoundException;
import com.project.translate.model.AppUser;
import com.project.translate.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepo userRepo;

    public AppUser getUserData(String userId) {
        return userRepo.getAppUserByPublicId(userId).orElseThrow(() ->
                new NotFoundException(
                        "USER_NOT_FOUND",
                        "User not found with given ID"
                )
        );
    }
}
