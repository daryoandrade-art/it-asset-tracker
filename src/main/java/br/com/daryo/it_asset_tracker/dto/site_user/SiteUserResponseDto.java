package br.com.daryo.it_asset_tracker.dto.site_user;

import java.time.LocalDateTime;

import br.com.daryo.it_asset_tracker.dto.site.SiteResponseDto;
import br.com.daryo.it_asset_tracker.model.SiteUser;

public record SiteUserResponseDto(
    Integer id,
    String name,
    String email,
    String phone,
    SiteResponseDto site,
    LocalDateTime createdAt
){
    public static SiteUserResponseDto fromEntity(SiteUser siteUser){
        return new SiteUserResponseDto(
            siteUser.getId(),
            siteUser.getName(),
            siteUser.getEmail(),
            siteUser.getPhone(),
            SiteResponseDto.fromEntity(siteUser.getSite()),
            siteUser.getCreatedAt()
        );
    }
}
