package com.example.ejercicios_spring_boot.controller;

import com.example.ejercicios_spring_boot.dto.LoginDto;
import com.example.ejercicios_spring_boot.dto.RegisterDto;
import com.example.ejercicios_spring_boot.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterDto dto) {
        return ResponseEntity.ok(
                authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto dto) {
        return ResponseEntity.ok(
                authService.login(dto));
    }
}
