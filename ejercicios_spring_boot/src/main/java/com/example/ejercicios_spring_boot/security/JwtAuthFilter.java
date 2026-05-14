package com.example.ejercicios_spring_boot.security;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 1. Validación inicial del Header
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        // Estructura básica del JWT (3 partes separadas por puntos)
        if (token.chars().filter(ch -> ch == '.').count() != 2) {
            enviarError(response, "Token con formato inválido");
            return; // ✅ Cortar la cadena, no continuar
        }

        try {
            String documento = jwtUtil.extraerDocumento(token);

            if (documento != null
                    && jwtUtil.isTokenValid(token, documento)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        documento, null, new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(auth);
            } else if (documento == null || !jwtUtil.isTokenValid(token, documento)) {
                // ✅ Token presente pero inválido/expirado → 401 con mensaje claro
                enviarError(response, "Token inválido o expirado");
                return;
            }

        } catch (Exception e) {
            // ✅ Cualquier error de parsing → 401 con mensaje, no stacktrace
            enviarError(response, "Error procesando el token: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    // ✅ Respuesta 401 en formato JSON (antes no existía esto)
    private void enviarError(HttpServletResponse response, String mensaje) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String timestamp = java.time.LocalDateTime.now().toString();
        String json = """
                {
                    "timestamp": "%s",
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "%s"
                }
                """.formatted(timestamp, mensaje);

        response.getWriter().write(json);
        response.getWriter().flush();
    }
    /*
     * // 2. Validación de estructura básica (el JWT debe tener 2 puntos/3 partes)
     * // Esto evita errores al intentar decodificar algo que no es un token.
     * if (token.chars().filter(ch -> ch == '.').count() != 2) {
     * filterChain.doFilter(request, response);
     * return;
     * }
     * 
     * String documento = jwtUtil.extraerDocumento(token);
     * 
     * // 3. Validaciones encadenadas por condicionales
     * if (documento != null &&
     * SecurityContextHolder.getContext().getAuthentication() == null) {
     * 
     * // Verificamos la validez del token
     * if (jwtUtil.isTokenValid(token, documento)) {
     * 
     * UsernamePasswordAuthenticationToken auth = new
     * UsernamePasswordAuthenticationToken(
     * documento,
     * null,
     * new ArrayList<>());
     * 
     * SecurityContextHolder.getContext().setAuthentication(auth);
     * }
     * }
     * 
     * filterChain.doFilter(request, response);
     */
}