package br.com.daryo.it_asset_tracker.model;

import java.time.LocalDateTime;

import br.com.daryo.it_asset_tracker.model.enums.AssetEnum;
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

@Entity(name = "asset_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status")
    private AssetEnum oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private AssetEnum newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_user_id")
    private SiteUser oldUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_user_id")
    private SiteUser newUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_site_id")
    private Site oldSite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_site_id")
    private Site newSite;

    @Column(name = "moved_at")
    private LocalDateTime movedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminUser admin;

}
