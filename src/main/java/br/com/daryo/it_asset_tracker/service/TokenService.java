package br.com.daryo.it_asset_tracker.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {

    private final Algorithm algorithm;
    private final long expirationSeconds;

    public TokenService(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.expiration:3600}") long expirationSeconds) {
            this.algorithm = Algorithm.HMAC256(secret);
            this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(Integer adminId, String email){
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expirationSeconds);

        return JWT.create()
                .withIssuer("it-asset-tracker")
                .withSubject(adminId.toString())
                .withClaim("email", email)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        return JWT.require(algorithm)
                .withIssuer("it-asset-tracker")
                .build()
                .verify(token);
    }

    public long getExpirationSeconds(){
        return expirationSeconds;
    }
}

