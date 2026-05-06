package controller;

import dto.LoginDto;
import dto.RegisterDto;
import services.AuthService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class AuthControlle {
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


