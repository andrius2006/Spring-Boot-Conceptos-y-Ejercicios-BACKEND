package com.example.ejercicios_spring_boot.services;

import com.example.ejercicios_spring_boot.models.Usuario;
import com.example.ejercicios_spring_boot.dto.LoginDto;
import com.example.ejercicios_spring_boot.dto.RegisterDto;
import com.example.ejercicios_spring_boot.services.AuthService;
import com.example.ejercicios_spring_boot.security.JwtUtil;
import com.example.ejercicios_spring_boot.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginDto dto) {
        Usuario usuario = usuarioRepo
                .findByEmail(dto.getEmail())
                .orElseThrow(() ->new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword()))
            throw new RuntimeException("Credenciales inválidas");
        return jwtUtil.generateToken(usuario.getEmail());
    }

    public String register(RegisterDto dto) {
        if (usuarioRepo.existsByEmail(dto.getEmail()))
            throw new RuntimeException("Email ya registrado");

        Usuario nuevo = Usuario.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nombre(dto.getNombre())
                .build();

        usuarioRepo.save(nuevo);
        return jwtUtil.generateToken(nuevo.getEmail());
    }
}
