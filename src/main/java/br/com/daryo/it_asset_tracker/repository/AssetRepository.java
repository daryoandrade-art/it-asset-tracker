package br.com.daryo.it_asset_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
}
