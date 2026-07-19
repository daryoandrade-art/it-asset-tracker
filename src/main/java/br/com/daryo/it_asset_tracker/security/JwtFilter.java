package br.com.daryo.it_asset_tracker.security;

import java.io.IOException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.daryo.it_asset_tracker.service.TokenService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter implements Filter {

    private final TokenService tokenService;

    public JwtFilter(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filter) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            sendUnauthorized(response, "Token not found");
            return;
        }

        String token = header.substring(7);

        try {
            DecodedJWT decoded = tokenService.validateToken(token);
            request.setAttribute("adminId", Integer.valueOf(decoded.getSubject()));
            request.setAttribute("adminEmail", decoded.getClaim("email").asString());
            filter.doFilter(request, response);
        } catch (JWTVerificationException e) {
            sendUnauthorized(response, "Token invalid or expired");
        }
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/problem+json");
        response.getWriter().write(
            "{\"type\":\"https://api.it-asset-tracker/errors/unauthorized\"," +
            "\"title\":\"Unauthorized\"," +
            "\"status\":401," +
            "\"detail\":\"" + message + "\"}"
        );
    }
}
