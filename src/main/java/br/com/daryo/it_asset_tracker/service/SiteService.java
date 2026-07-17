package br.com.daryo.it_asset_tracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.daryo.it_asset_tracker.dto.site.SiteRequestDto;
import br.com.daryo.it_asset_tracker.dto.site.SiteResponseDto;
import br.com.daryo.it_asset_tracker.model.Site;
import br.com.daryo.it_asset_tracker.model.enums.SiteEnum;
import br.com.daryo.it_asset_tracker.repository.SiteRepository;

@Service
public class SiteService {

    private final SiteResponseDto siteResponseDto;
    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository, SiteResponseDto siteResponseDto){
        this.siteRepository = siteRepository;
        this.siteResponseDto = siteResponseDto;
    }


    public SiteResponseDto create(SiteRequestDto dto){
        Site site = dto.toEntity();
        site.setStatusContract(SiteEnum.ACTIVE);
        siteRepository.save(site);
        return SiteResponseDto.fromEntity(site);
    }

    public List<SiteResponseDto> findAll(){
        List<Site> sites = siteRepository.findAll();

        return sites.stream()
                    .map(SiteResponseDto::fromEntity)
                    .collect(Collectors.toList());
    }

    public SiteResponseDto findById(Integer id){
        return SiteResponseDto.fromEntity(siteRepository.searchById(id));
    }

    public SiteResponseDto inactive(SiteRequestDto dto){
        Site site = dto.toEntity();
        site.setStatusContract(SiteEnum.INACTIVE);
        siteRepository.save(site);
        return SiteResponseDto.fromEntity(site);
    }

    public SiteResponseDto update(Integer id, SiteRequestDto dto){
        Site site = siteRepository.searchById(id);
        site.setName(dto.name());
        site.setDomain(dto.domain());
        siteRepository.save(site);
        return SiteResponseDto.fromEntity(site);
    }

}
