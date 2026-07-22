package br.com.daryo.it_asset_tracker.dto.site_user;

import br.com.daryo.it_asset_tracker.model.Site;
import br.com.daryo.it_asset_tracker.model.SiteUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SiteUserRequestDto(
    @NotBlank(message = "[name] is mandatory")
    String name,

    @NotBlank(message = "[phone] is mandatory")
    String phone,

    @NotBlank(message = "[email] is mandatory")
    @Email(message = "Insert a valid email")
    String email,

    @NotNull(message = "[siteId] is mandatory")
    Integer siteId
) {
    public SiteUser toEntity(Site site) {
        SiteUser siteUser = new SiteUser();
        siteUser.setName(this.name());
        siteUser.setPhone(this.phone());
        siteUser.setEmail(this.email());
        siteUser.setSite(site);
        return siteUser;
    }
}
