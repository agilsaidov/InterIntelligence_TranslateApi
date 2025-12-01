package com.project.translate.controller;

import com.project.translate.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService userService;

    @GetMapping
    public ResponseEntity<?> getUserData(@AuthenticationPrincipal String userId) {
        return new ResponseEntity<>(userService.getUserData(userId), HttpStatus.OK);
    }


}
