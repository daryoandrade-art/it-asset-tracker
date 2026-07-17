package br.com.daryo.it_asset_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daryo.it_asset_tracker.model.MaintenanceRecord;
import br.com.daryo.it_asset_tracker.model.enums.MaintenanceStatus;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Integer> {

    List<MaintenanceRecord> findByAssetId(Integer assetId);

    List<MaintenanceRecord> findByAssetIdAndStatus(Integer assetId, MaintenanceStatus status);
}
