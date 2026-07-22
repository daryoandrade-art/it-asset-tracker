package br.com.daryo.it_asset_tracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.daryo.it_asset_tracker.dto.site_user.SiteUserRequestDto;
import br.com.daryo.it_asset_tracker.dto.site_user.SiteUserResponseDto;
import br.com.daryo.it_asset_tracker.exception.DuplicateResourceException;
import br.com.daryo.it_asset_tracker.exception.InvalidArgumentException;
import br.com.daryo.it_asset_tracker.exception.ResourceNotFoundException;
import br.com.daryo.it_asset_tracker.model.Site;
import br.com.daryo.it_asset_tracker.model.SiteUser;
import br.com.daryo.it_asset_tracker.model.enums.SiteEnum;
import br.com.daryo.it_asset_tracker.repository.SiteRepository;
import br.com.daryo.it_asset_tracker.repository.SiteUserRepository;

@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final SiteRepository siteRepository;

    public SiteUserService(SiteUserRepository siteUserRepository, SiteRepository siteRepository){
        this.siteUserRepository = siteUserRepository;
        this.siteRepository = siteRepository;
    }

    public SiteUserResponseDto create(SiteUserRequestDto dto){
        Site site = siteRepository.findById(dto.siteId())
                .orElseThrow(() -> new InvalidArgumentException("site does not exist"));

        if (site.getStatusContract() != SiteEnum.ACTIVE){
            throw new InvalidArgumentException("site is inactive");
        }
        if (siteUserRepository.findByEmail(dto.email()).isPresent()){
            throw new DuplicateResourceException("email must be unique");
        }
        if (siteUserRepository.findByPhone(dto.phone()).isPresent()){
            throw new DuplicateResourceException("phone must be unique");
        }
        SiteUser siteUser = dto.toEntity(site);
        siteUserRepository.save(siteUser);
        return SiteUserResponseDto.fromEntity(siteUser);
    }

    public List<SiteUserResponseDto> listBySite(Integer siteId){
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("site not found"));
        return siteUserRepository.findBySite(site).stream()
                .map(SiteUserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
    public List<SiteUserResponseDto> findAll(){
        return siteUserRepository.findAll().stream()
                .map(SiteUserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public SiteUserResponseDto findById(Integer id){
        SiteUser siteUser = siteUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return SiteUserResponseDto.fromEntity(siteUser);
    }

    public void delete(Integer id){
        SiteUser siteUser = siteUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        siteUserRepository.delete(siteUser);
    }
    public SiteUserResponseDto update(Integer id, SiteUserRequestDto dto){
        Site site = siteRepository.findById(dto.siteId())
                .orElseThrow(() -> new ResourceNotFoundException("site not found"));
        SiteUser siteUser = siteUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        if (site.getStatusContract() != SiteEnum.ACTIVE){
            throw new InvalidArgumentException("users is only permitted for active sites");
        }
        siteUser.setName(dto.name());
        siteUser.setPhone(dto.phone());
        siteUser.setEmail(dto.email());
        siteUser.setSite(site);
        siteUserRepository.save(siteUser);
        return SiteUserResponseDto.fromEntity(siteUser);
    }
}