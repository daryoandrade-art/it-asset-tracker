package br.com.daryo.it_asset_tracker.dto.site;

import java.time.LocalDateTime;

import br.com.daryo.it_asset_tracker.model.Site;
import br.com.daryo.it_asset_tracker.model.enums.SiteEnum;

public record SiteResponseDto(
    Integer id,
    String name,
    SiteEnum status,
    String domain,
    LocalDateTime createdAt
) {
    public static SiteResponseDto fromEntity(Site site){
        return new SiteResponseDto(
            site.getId(),
            site.getName(),
            site.getStatusContract(),
            site.getDomain(),
            site.getCreatedAt()
        );
    }
}
