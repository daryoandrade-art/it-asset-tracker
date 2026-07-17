package br.com.daryo.it_asset_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.SiteUser;

public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
}
