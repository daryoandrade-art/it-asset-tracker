package br.com.daryo.it_asset_tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.daryo.it_asset_tracker.dto.auth.LoginRequest;
import br.com.daryo.it_asset_tracker.dto.auth.RegisterRequest;
import br.com.daryo.it_asset_tracker.dto.auth.TokenResponse;
import br.com.daryo.it_asset_tracker.exception.DuplicateResourceException;
import br.com.daryo.it_asset_tracker.exception.InvalidCredentialsException;
import br.com.daryo.it_asset_tracker.model.AdminUser;
import br.com.daryo.it_asset_tracker.repository.AdminUserRepository;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @Mock
    private AdminUserRepository adminUserRepository;

    @Mock
    private TokenService tokenService;

    private AdminUserService adminUserService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        adminUserService = new AdminUserService(adminUserRepository, tokenService);
    }

    @Test
    void shouldRegisterSuccessfully() {
        RegisterRequest request = new RegisterRequest("Admin", "11999999999", "admin@test.com", "123456");

        when(adminUserRepository.findByEmail("admin@test.com")).thenReturn(Optional.empty());
        when(adminUserRepository.save(any(AdminUser.class))).thenAnswer(invocation -> {
            AdminUser saved = invocation.getArgument(0);
            saved.setId(1);
            return saved;
        });
        when(tokenService.generateToken(1, "admin@test.com")).thenReturn("fake-jwt-token");
        when(tokenService.getExpirationSeconds()).thenReturn(3600L);

        TokenResponse response = adminUserService.register(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.token());
        assertEquals(3600L, response.expiresIn());
        verify(adminUserRepository).save(any(AdminUser.class));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("Admin", "11999999999", "admin@test.com", "123456");

        when(adminUserRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(new AdminUser()));

        assertThrows(DuplicateResourceException.class, () -> {
            adminUserService.register(request);
        });

        verify(adminUserRepository, never()).save(any());
    }

    @Test
    void shouldLoginSuccessfully() {
        LoginRequest request = new LoginRequest("admin@test.com", "123456");

        AdminUser admin = new AdminUser();
        admin.setId(1);
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("123456"));

        when(adminUserRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));
        when(tokenService.generateToken(1, "admin@test.com")).thenReturn("fake-jwt-token");
        when(tokenService.getExpirationSeconds()).thenReturn(3600L);

        TokenResponse response = adminUserService.login(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.token());
    }

    @Test
    void shouldThrowWhenEmailNotFound() {
        LoginRequest request = new LoginRequest("wrong@test.com", "123456");

        when(adminUserRepository.findByEmail("wrong@test.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> {
            adminUserService.login(request);
        });
    }

    @Test
    void shouldThrowWhenPasswordIsWrong() {
        LoginRequest request = new LoginRequest("admin@test.com", "senha-errada");

        AdminUser admin = new AdminUser();
        admin.setId(1);
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("123456"));

        when(adminUserRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));

        assertThrows(InvalidCredentialsException.class, () -> {
            adminUserService.login(request);
        });
    }
}
