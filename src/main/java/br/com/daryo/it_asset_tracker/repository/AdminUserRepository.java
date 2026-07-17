package br.com.daryo.it_asset_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

    Optional<AdminUser> findByEmail(String email);
}
