package br.com.daryo.it_asset_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daryo.it_asset_tracker.dto.auth.LoginRequest;
import br.com.daryo.it_asset_tracker.dto.auth.RegisterRequest;
import br.com.daryo.it_asset_tracker.dto.auth.TokenResponse;
import br.com.daryo.it_asset_tracker.service.AdminUserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AdminUserService adminUserService;

    public AuthController(AdminUserService adminUserService){
        this.adminUserService = adminUserService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        TokenResponse response = adminUserService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = adminUserService.login(request);
        return ResponseEntity.ok(response);
    }
}
