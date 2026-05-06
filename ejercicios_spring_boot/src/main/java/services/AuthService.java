package services;

import models.Usuario;
import dto.LoginDto;
import dto.RegisterDto;
import services.AuthService;
import security.JwtService;
import repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {



    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public String login(LoginDto dto) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getPassword()
            )
        );
        Usuario usuario = usuarioRepo
            .findByEmail(dto.getEmail())
            .orElseThrow();
        return jwtService.generateToken(usuario.getEmail());
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
        return jwtService.generateToken(nuevo.getEmail());
    }
}


