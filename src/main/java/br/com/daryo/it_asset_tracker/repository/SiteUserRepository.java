package br.com.daryo.it_asset_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.Site;
import br.com.daryo.it_asset_tracker.model.SiteUser;


public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
    SiteUser searchById(Integer id);
    SiteUser searchByEmail(String email);
    SiteUser searchByPhone(String phone);
    List<SiteUser> findBySite(Site site);
}
