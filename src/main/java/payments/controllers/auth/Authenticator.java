package payments.controllers.auth;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Authenticator {
    private Key secretKey;

    public Authenticator() {
        final String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; ++i) {
            sb.append(pool.charAt((int) (pool.length() * Math.random())));
        }
        String secret = sb.toString();
        secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
    }

    public String createJwt(String email, boolean isAdmin) {
        Instant now = Instant.now();
        return Jwts
                .builder()
                .setSubject(email)
                .claim("isAdmin", isAdmin)
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact();
    }

    private Context getContextFromJwt(String jwt) {
        try {
            Jws<Claims> token = Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);
            return new Context(token.getBody().getSubject(), token.getBody().get("isAdmin", Boolean.class));
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid auth token");
        }
    }

    public Context getContextOrFail(String authHeader) {
        if (!authHeader.startsWith("Bearer")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid Authorization header. Must be: 'Bearer YOUR_JWT_TOKEN'");
        }
        return getContextFromJwt(authHeader.substring(7));
    }
}
