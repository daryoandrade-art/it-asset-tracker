package br.com.daryo.it_asset_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.Asset;
import br.com.daryo.it_asset_tracker.model.SiteUser;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
    List<Asset> findByUser(SiteUser siteUser);
}
