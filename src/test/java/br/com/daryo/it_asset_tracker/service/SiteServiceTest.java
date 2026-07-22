package br.com.daryo.it_asset_tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.daryo.it_asset_tracker.dto.site.SiteRequestDto;
import br.com.daryo.it_asset_tracker.dto.site.SiteResponseDto;
import br.com.daryo.it_asset_tracker.exception.InvalidArgumentException;
import br.com.daryo.it_asset_tracker.exception.ResourceNotFoundException;
import br.com.daryo.it_asset_tracker.model.Site;
import br.com.daryo.it_asset_tracker.model.enums.SiteEnum;
import br.com.daryo.it_asset_tracker.repository.SiteRepository;

@ExtendWith(MockitoExtension.class)
class SiteServiceTest {

    @Mock
    private SiteRepository siteRepository;

    private SiteService siteService;

    @BeforeEach
    void setUp() {
        siteService = new SiteService(siteRepository);
    }
    @Test
    void shouldCreateSiteWithActiveStatus() {
        SiteRequestDto dto = new SiteRequestDto("Filial Centro", "filial-centro.com");

        when(siteRepository.save(any(Site.class))).thenAnswer(invocation -> {
            Site saved = invocation.getArgument(0);
            saved.setId(1);
            return saved;
        });

        SiteResponseDto response = siteService.create(dto);

        assertEquals("Filial Centro", response.name());
        assertEquals(SiteEnum.ACTIVE, response.status());
        verify(siteRepository).save(any(Site.class));
    }

    @Test
    void shouldFindByIdSuccessfully() {
        Site site = new Site();
        site.setId(1);
        site.setName("Filial Centro");
        site.setStatusContract(SiteEnum.ACTIVE);

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));

        SiteResponseDto response = siteService.findById(1);

        assertEquals(1, response.id());
        assertEquals("Filial Centro", response.name());
    }

    @Test
    void shouldThrowWhenSiteNotFoundOnFindById() {
        when(siteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            siteService.findById(99);
        });
    }


    @Test
    void shouldInactivateSuccessfully() {
        Site site = new Site();
        site.setId(1);
        site.setStatusContract(SiteEnum.ACTIVE);

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));
        when(siteRepository.save(any(Site.class))).thenReturn(site);

        SiteResponseDto response = siteService.inactive(1);

        assertEquals(SiteEnum.INACTIVE, response.status());
        verify(siteRepository).save(site);
    }

    @Test
    void shouldThrowWhenInactivatingAlreadyInactiveSite() {
        Site site = new Site();
        site.setId(1);
        site.setStatusContract(SiteEnum.INACTIVE);

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));

        assertThrows(InvalidArgumentException.class, () -> {
            siteService.inactive(1);
        });

        verify(siteRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenSiteNotFoundOnInactive() {
        when(siteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            siteService.inactive(99);
        });

        verify(siteRepository, never()).save(any());
    }


    @Test
    void shouldActivateSuccessfully() {
        Site site = new Site();
        site.setId(1);
        site.setStatusContract(SiteEnum.INACTIVE);

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));
        when(siteRepository.save(any(Site.class))).thenReturn(site);

        SiteResponseDto response = siteService.active(1);

        assertEquals(SiteEnum.ACTIVE, response.status());
        verify(siteRepository).save(site);
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveSite() {
        Site site = new Site();
        site.setId(1);
        site.setStatusContract(SiteEnum.ACTIVE);

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));

        assertThrows(InvalidArgumentException.class, () -> {
            siteService.active(1);
        });

        verify(siteRepository, never()).save(any());
    }


    @Test
    void shouldUpdateSuccessfully() {
        Site site = new Site();
        site.setId(1);
        site.setName("Nome antigo");
        site.setDomain("dominio-antigo.com");
        site.setStatusContract(SiteEnum.ACTIVE);

        SiteRequestDto dto = new SiteRequestDto("Nome novo", "dominio-novo.com");

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));
        when(siteRepository.save(any(Site.class))).thenReturn(site);

        SiteResponseDto response = siteService.update(1, dto);

        assertEquals("Nome novo", response.name());
        assertEquals("dominio-novo.com", response.domain());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentSite() {
        SiteRequestDto dto = new SiteRequestDto("Nome novo", "dominio-novo.com");

        when(siteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            siteService.update(99, dto);
        });

        verify(siteRepository, never()).save(any());
    }


    @Test
    void shouldListAllSites() {
        Site site1 = new Site();
        site1.setId(1);
        site1.setName("Filial A");
        site1.setStatusContract(SiteEnum.ACTIVE);

        Site site2 = new Site();
        site2.setId(2);
        site2.setName("Filial B");
        site2.setStatusContract(SiteEnum.ACTIVE);

        when(siteRepository.findAll()).thenReturn(List.of(site1, site2));

        List<SiteResponseDto> response = siteService.findAll();

        assertEquals(2, response.size());
    }
}