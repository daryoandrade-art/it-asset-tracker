// src/test/java/br/com/daryo/it_asset_tracker/service/TokenServiceTest.java

package br.com.daryo.it_asset_tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService("minha-chave-secreta-para-testes", 3600);
    }

    @Test
    void shouldGenerateValidToken() {
        String token = tokenService.generateToken(1, "admin@test.com");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldValidateToken() {
        String token = tokenService.generateToken(1, "admin@test.com");
        DecodedJWT decoded = tokenService.validateToken(token);

        assertEquals("1", decoded.getSubject());
        assertEquals("admin@test.com", decoded.getClaim("email").asString());
        assertEquals("it-asset-tracker", decoded.getIssuer());
    }

    @Test
    void shouldRejectInvalidToken() {
        assertThrows(JWTVerificationException.class, () -> {
            tokenService.validateToken("token.invalido.aqui");
        });
    }

    @Test
    void shouldRejectTokenWithWrongSecret() {
        TokenService otherService = new TokenService("outra-chave-diferente", 3600);
        String token = otherService.generateToken(1, "admin@test.com");

        assertThrows(JWTVerificationException.class, () -> {
            tokenService.validateToken(token);
        });
    }
}
