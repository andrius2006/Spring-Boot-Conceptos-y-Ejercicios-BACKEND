package security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {



    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String email) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(
                System.currentTimeMillis() + expiration))
            .signWith(getSignKey())
            .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
            .verifyWith(getSignKey()).build()
            .parseSignedClaims(token)
            .getPayload().getSubject();
    }

    public boolean isValid(String token, UserDetails user) {
        String email = extractEmail(token);
        return email.equals(user.getUsername())
            && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return Jwts.parser()
            .verifyWith(getSignKey()).build()
            .parseSignedClaims(token)
            .getPayload().getExpiration()
            .before(new Date());
    }

    private SecretKey getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
    

