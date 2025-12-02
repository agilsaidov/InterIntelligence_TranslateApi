package com.project.translate.controller;

import com.project.translate.dto.request.UserDataUpdateRequest;
import com.project.translate.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserData(@AuthenticationPrincipal String userId) {
        return new ResponseEntity<>(userService.getUserData(userId), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateUserData(@AuthenticationPrincipal String userId,
                                            @RequestBody UserDataUpdateRequest request) {
        return new ResponseEntity<>(userService.updateUserData(userId, request), HttpStatus.OK);

    }


}
