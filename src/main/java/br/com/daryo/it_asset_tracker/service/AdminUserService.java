package br.com.daryo.it_asset_tracker.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.daryo.it_asset_tracker.dto.auth.LoginRequest;
import br.com.daryo.it_asset_tracker.dto.auth.RegisterRequest;
import br.com.daryo.it_asset_tracker.dto.auth.TokenResponse;
import br.com.daryo.it_asset_tracker.exception.DuplicateResourceException;
import br.com.daryo.it_asset_tracker.exception.InvalidCredentialsException;
import br.com.daryo.it_asset_tracker.model.AdminUser;
import br.com.daryo.it_asset_tracker.repository.AdminUserRepository;

@Service
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final TokenService tokenService;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserService(AdminUserRepository adminUserRepository, TokenService tokenService) {
        this.adminUserRepository = adminUserRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public TokenResponse register(RegisterRequest request) {
        adminUserRepository.findByEmail(request.email()).ifPresent(existing -> {
            throw new DuplicateResourceException("Email is in use");
        });

        AdminUser admin = new AdminUser();
        admin.setName(request.name());
        admin.setPhone(request.phone());
        admin.setEmail(request.email());
        admin.setPassword(passwordEncoder.encode(request.password()));

        admin = adminUserRepository.save(admin);

        String token = tokenService.generateToken(admin.getId(), admin.getEmail());
        return new TokenResponse(token, tokenService.getExpirationSeconds());
    }

    public TokenResponse login(LoginRequest request){
        AdminUser admin = adminUserRepository.findByEmail(request.email()).orElseThrow(() -> new InvalidCredentialsException("Email not found"));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())){
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = tokenService.generateToken(admin.getId(), admin.getEmail());
        return new TokenResponse(token, tokenService.getExpirationSeconds());
    }
}
