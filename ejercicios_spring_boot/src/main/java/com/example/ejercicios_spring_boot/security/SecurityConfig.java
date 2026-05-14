package com.example.ejercicios_spring_boot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Habilitamos CORS con la configuración definida abajo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 2. Deshabilitamos CSRF (necesario para APIs que usan POST/PUT con JWT)
                .csrf(csrf -> csrf.disable())
                // 3. Manejador de acceso denegado
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
                // 4. Configuramos las reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/usuarios/**").permitAll()
                        .requestMatchers("/api/productos/**").permitAll()
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())
                // 3. Política de sesión sin estado (Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 4. Filtro JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Configuración detallada de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite cualquier origen (puedes cambiar "*" por la URL de tu frontend)
        configuration.setAllowedOrigins(List.of("*"));
        // Permite los métodos HTTP principales
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite todos los headers (incluyendo Authorization)
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Headers que el cliente puede exponer
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}