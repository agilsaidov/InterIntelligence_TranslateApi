package com.project.translate.controller;

import com.project.translate.dto.request.ChangePasswordRequest;
import com.project.translate.dto.request.UserDataUpdateRequest;
import com.project.translate.dto.response.UserResponse;
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
    public ResponseEntity<UserResponse> getUserData(@AuthenticationPrincipal String userId) {
        return new ResponseEntity<>(userService.getUserData(userId), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserResponse> updateUserData(@AuthenticationPrincipal String userId,
                                            @RequestBody UserDataUpdateRequest request) {
        return new ResponseEntity<>(userService.updateUserData(userId, request), HttpStatus.OK);

    }

    @PostMapping("/changepassword")
    public ResponseEntity<Void> changeUserPassword(@AuthenticationPrincipal String userId,
                                                @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal String userId,
                                           @RequestHeader("Authorization") String token) {
        userService.softDeleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
