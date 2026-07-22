package br.com.daryo.it_asset_tracker.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import br.com.daryo.it_asset_tracker.model.enums.MaintenanceStatus;
import br.com.daryo.it_asset_tracker.model.enums.MaintenanceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "maintenance_record")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "maintenance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaintenanceType maintenanceType;

    @Column(nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_admin_id")
    private AdminUser adminUser;

    @Column(name = "performed_at")
    private LocalDateTime performedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "asset_serial_number", nullable = false)
    private String assetSerialNumber;
}
