package br.com.daryo.it_asset_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.Site;
import br.com.daryo.it_asset_tracker.model.SiteUser;


public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByEmail(String email);
    Optional<SiteUser> findByPhone(String phone);
    List<SiteUser> findBySite(Site site);
}
