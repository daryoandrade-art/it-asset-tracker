package br.com.daryo.it_asset_tracker.dto.auth;

public record TokenResponse(
    String token,
    String type,
    long expiresIn
) {
    public TokenResponse(String token, long expiresIn){
        this(token, "Bearer", expiresIn);
    }
}
