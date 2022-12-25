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
import payments.controllers.exceptions.InvalidAuthHeaderException;

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

    public String createJwt(String email) {
        Instant now = Instant.now();
        return Jwts
                .builder()
                .setSubject(email)
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact();
    }

    public Context getContextFromJwt(String jwt) {
        try {
            Jws<Claims> token = Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);
            return new Context(token.getBody().getSubject());
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid auth token");
        }
    }

    public Context getContextFromAuthHeader(String authHeader) throws InvalidAuthHeaderException {
        if (!authHeader.startsWith("Bearer")) {
            throw new InvalidAuthHeaderException();
        }
        return getContextFromJwt(authHeader.substring(7));
    }
}
