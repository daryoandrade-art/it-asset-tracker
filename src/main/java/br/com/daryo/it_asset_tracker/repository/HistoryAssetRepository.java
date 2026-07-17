package br.com.daryo.it_asset_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.HistoryAsset;

public interface HistoryAssetRepository extends JpaRepository<HistoryAsset, Integer> {

    List<HistoryAsset> findByAssetIdOrderByMovedAtDesc(Integer assetId);
}
