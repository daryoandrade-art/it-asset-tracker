package br.com.daryo.it_asset_tracker.dto.auth;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
    @NotBlank(message = "[name] is mandatory")
    String name,

    @NotBlank(message = "[email] is mandatory")
    @Email
    String email,
    
    @NotBlank(message = "[phone] is mandatory")
    String phone,

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*()_\\-]).{8,30}$",
        message = "[password] must be between 8 and 30 characters long, with at least one uppercase letter, one lowercase letter, one number, and one symbol")
    String password
) {
    
}
