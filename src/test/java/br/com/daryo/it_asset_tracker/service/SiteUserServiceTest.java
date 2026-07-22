package br.com.daryo.it_asset_tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class SiteUserServiceTest {

    @Mock
    private SiteUserRepository siteUserRepository;
    @Mock
    private SiteRepository siteRepository;

    private SiteUserService siteUserService;

    @BeforeEach
    void setUp() {
        siteUserService = new SiteUserService(siteUserRepository, siteRepository);
    }
    //Create
    @Test
    void shouldCreateSiteUserSuccessfully(){

        Site site = new Site();
        site.setId(1);
        site.setName("Filial Centro");
        site.setStatusContract(SiteEnum.ACTIVE);

        SiteUserRequestDto dto = new SiteUserRequestDto("daryo", "8899999999", "daryodev@dev.com", 1);

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));

        when(siteUserRepository.save(any(SiteUser.class))).thenAnswer(invocation -> {
            SiteUser saved = invocation.getArgument(0);
            saved.setId(1);
            return saved;
        });

        SiteUserResponseDto response = siteUserService.create(dto);

        assertEquals("daryo", response.name());
        assertEquals("8899999999", response.phone());
        assertEquals("daryodev@dev.com", response.email());
        assertEquals(1, response.site().id());
    }

    @Test
    void shouldThrowWhenSiteDoesNotExist(){
        SiteUserRequestDto dto = new SiteUserRequestDto("daryo", "88999999999", "daryodev@dev.com", 99);

        when(siteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(InvalidArgumentException.class, () -> {
            siteUserService.create(dto);
        });
    }

    @Test
    void shouldThrowWhenSiteIsInactive(){
        Site site = new Site();
        site.setId(99);
        site.setName("Filial Centro");
        site.setStatusContract(SiteEnum.INACTIVE);

        SiteUserRequestDto dto = new SiteUserRequestDto("daryo", "88999999999", "daryodev@dev.com", 99);

        when(siteRepository.findById(99)).thenReturn(Optional.of(site));

        assertThrows(InvalidArgumentException.class, () -> {
            siteUserService.create(dto);
        });
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        Site site = new Site();
        site.setId(99);
        site.setName("Filial Centro");
        site.setStatusContract(SiteEnum.ACTIVE);

        SiteUser existingUser = new SiteUser();
        existingUser.setName("Daryo");
        existingUser.setPhone("8899999999");
        existingUser.setId(1);
        existingUser.setEmail("daryodev@dev.com");
        existingUser.setSite(site);

        SiteUserRequestDto dto = new SiteUserRequestDto(
                "daryos",
                "8888888888",
                "daryodev@dev.com",
                99
        );

        when(siteRepository.findById(99)).thenReturn(Optional.of(site));

        when(siteUserRepository.findByEmail("daryodev@dev.com"))
                .thenReturn(Optional.of(existingUser));

        assertThrows(DuplicateResourceException.class, () -> {
            siteUserService.create(dto);
        });
    }

    @Test
    void shouldThrowWhenPhoneAlreadyExists() {
        Site site = new Site();
        site.setId(99);
        site.setName("Filial Centro");
        site.setStatusContract(SiteEnum.ACTIVE);

        SiteUser existingUser = new SiteUser();
        existingUser.setName("Daryo");
        existingUser.setPhone("8899999999");
        existingUser.setId(1);
        existingUser.setEmail("outro@email.com");
        existingUser.setSite(site);

        SiteUserRequestDto dto = new SiteUserRequestDto(
                "daryos",
                "8899999999",
                "daryodev@dev.com",
                99
        );

        when(siteRepository.findById(99)).thenReturn(Optional.of(site));
        when(siteUserRepository.findByEmail("daryodev@dev.com")).thenReturn(Optional.empty());
        when(siteUserRepository.findByPhone("8899999999")).thenReturn(Optional.of(existingUser));

        assertThrows(DuplicateResourceException.class, () -> {
            siteUserService.create(dto);
        });
    }

    // FIND BY SITE
    @Test
    void shouldListBySiteSuccessfully(){
        Site site = new Site();
        site.setId(1);
        site.setName("Filial A");
        site.setStatusContract(SiteEnum.ACTIVE);

        SiteUser user1 = new SiteUser();
        user1.setId(1);
        user1.setName("Daryo");
        user1.setEmail("daryodev@dev.com");
        user1.setPhone("8899999999");
        user1.setSite(site);

        SiteUser user2 = new SiteUser();
        user2.setId(2);
        user2.setName("Daryo");
        user2.setEmail("daryodev2@dev.com");
        user2.setPhone("8899999998");
        user2.setSite(site);

        when(siteRepository.findById(1)).thenReturn(Optional.of(site));
        when(siteUserRepository.findBySite(site)).thenReturn(List.of(user1, user2));

        List<SiteUserResponseDto> response = siteUserService.listBySite(site.getId());

        assertEquals(2, response.size());
    }

    @Test
    void shouldThrowWhenListingBySiteNotFound(){
        when(siteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            siteUserService.listBySite(99);
        });
    }

    @Test
    void shouldFindByIdSuccessfully(){
        Site site = new Site();
        site.setId(1);
        site.setName("Filial Centro");
        site.setStatusContract(SiteEnum.ACTIVE);

        SiteUser user1 = new SiteUser();
        user1.setId(1);
        user1.setName("Daryo");
        user1.setEmail("daryodev@dev.com");
        user1.setPhone("8899999999");
        user1.setSite(site);

        when(siteUserRepository.findById(1)).thenReturn(Optional.of(user1));

        SiteUserResponseDto response = siteUserService.findById(1);

        assertEquals(1, response.id());
        assertEquals("Daryo", response.name());
        assertEquals("daryodev@dev.com", response.email());
        assertEquals("8899999999", response.phone());
    }

    @Test
    void shouldThrowWhenUserNotFound(){
        when(siteUserRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            siteUserService.findById(999);
        });
    }

    @Test
    void shouldDeleteSuccessfully(){
        Site site = new Site();
        site.setId(1);
        site.setName("Filial Centro");
        site.setStatusContract(SiteEnum.ACTIVE);

        SiteUser user = new SiteUser();
        user.setId(1);
        user.setName("Daryo");
        user.setEmail("daryodev@dev.com");
        user.setPhone("8899999999");
        user.setSite(site);

        when(siteUserRepository.findById(1)).thenReturn(Optional.of(user));

        siteUserService.delete(1);

        verify(siteUserRepository).delete(user);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentUser(){
        when(siteUserRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            siteUserService.delete(999);
        });
    }

}