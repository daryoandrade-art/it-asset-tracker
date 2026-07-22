package br.com.daryo.it_asset_tracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daryo.it_asset_tracker.dto.site_user.SiteUserRequestDto;
import br.com.daryo.it_asset_tracker.dto.site_user.SiteUserResponseDto;
import br.com.daryo.it_asset_tracker.service.SiteUserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sites/user")
public class SiteUserController {
    private final SiteUserService siteUserService;
    public SiteUserController(SiteUserService siteUserService){
        this.siteUserService = siteUserService;
    }

    @PostMapping("/new")
    public ResponseEntity<SiteUserResponseDto> create(@Valid @RequestBody SiteUserRequestDto dto){
        SiteUserResponseDto response = siteUserService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{siteId}")
    public ResponseEntity<List<SiteUserResponseDto>> findBySite(@PathVariable Integer siteId){
        return ResponseEntity.status(HttpStatus.OK).body(siteUserService.listBySite(siteId));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<SiteUserResponseDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(siteUserService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<SiteUserResponseDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(siteUserService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteUserResponseDto> update(@PathVariable Integer id, @Valid @RequestBody SiteUserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(siteUserService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        siteUserService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
