package br.com.daryo.it_asset_tracker.dto.site;

import br.com.daryo.it_asset_tracker.model.Site;
import jakarta.validation.constraints.NotBlank;

public record SiteRequestDto(
    @NotBlank(message = "[Name] is mandatory")
    String name,
    @NotBlank(message = "[Domain] is mandatory")
    String domain
) {
    public Site toEntity(){
        Site site = new Site();
        site.setName(this.name());
        site.setDomain(this.domain());
        return site;
    }
}
