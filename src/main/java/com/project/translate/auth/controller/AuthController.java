package com.project.translate.auth.controller;

import com.project.translate.dto.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {

    }
}
