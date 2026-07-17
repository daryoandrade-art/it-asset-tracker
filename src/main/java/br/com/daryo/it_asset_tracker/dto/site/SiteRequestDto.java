package br.com.daryo.it_asset_tracker.dto.site;

import br.com.daryo.it_asset_tracker.model.Site;

public record SiteRequestDto(
    String name,
    String domain
) {
    public Site toEntity(){
        Site site = new Site();
        site.setName(this.name());
        site.setDomain(this.domain());
        return site;
    }
}
