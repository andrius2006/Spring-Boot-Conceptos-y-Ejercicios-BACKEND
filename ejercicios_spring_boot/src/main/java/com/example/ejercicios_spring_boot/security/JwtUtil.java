package com.example.ejercicios_spring_boot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "mi_clave_secreta_super_larga_para_jwt_256bits!!";

    public String generateToken(String documento) {
        return Jwts.builder()
                .subject(documento)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24)) // 24h
                .signWith(getSigningKey())
                .compact();
    }

    public String extraerDocumento(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return extraerDocumento(token).equals(username) && !isTokenExpired(token);
    }

    // ✅ FIX: Antes lanzaba UnsupportedOperationException — ahora sí retorna el
    // username
    // Valida firma y expiración; retorna el username si es válido, null si no
    public String validateTokenAndGetUsername(String token) {
        try {
            String username = extraerDocumento(token);
            if (username != null && !isTokenExpired(token)) {
                return username;
            }
            return null;
        } catch (Exception e) {
            // Token malformado, firma inválida o expirado → retorna null
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}