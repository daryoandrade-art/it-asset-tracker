package br.com.daryo.it_asset_tracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daryo.it_asset_tracker.dto.site.SiteRequestDto;
import br.com.daryo.it_asset_tracker.dto.site.SiteResponseDto;
import br.com.daryo.it_asset_tracker.service.SiteService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping("/new")
    public ResponseEntity<SiteResponseDto> create(@Valid @RequestBody SiteRequestDto dto) {
        SiteResponseDto response = siteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SiteResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(siteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponseDto> findById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(siteService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteResponseDto> update(@PathVariable Integer id, @Valid @RequestBody SiteRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(siteService.update(id, dto));
    }

    @PatchMapping("/{id}/inactive")
    public ResponseEntity<SiteResponseDto> inactive(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(siteService.inactive(id));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<SiteResponseDto> active(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(siteService.active(id));
    }

}
