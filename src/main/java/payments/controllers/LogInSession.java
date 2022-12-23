package payments.controllers;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LogInSession {
    private Key secretKey;

    public LogInSession() {
        String secret = "ALSDAIOUOQWIR7987132KJHDASKDJH92391287KJSHDKAJHD12987391489631qjkASJHDG";
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
        Jws<Claims> token = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt);
        return new Context(token.getBody().getSubject());
    }
}
