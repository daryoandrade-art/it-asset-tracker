package br.com.daryo.it_asset_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.Site;

public interface SiteRepository extends JpaRepository<Site, Integer> {
    Site searchById(Integer id);
}
