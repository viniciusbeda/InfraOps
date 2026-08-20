package com.infraops.backend.controller;

import com.infraops.backend.entity.Asset;
import com.infraops.backend.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<Asset>> findAll() {
        return ResponseEntity.ok(assetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> findById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Asset> create(@RequestBody Asset asset) {
        Asset createdAsset = assetService.create(asset);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAsset);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> update(
            @PathVariable Long id,
            @RequestBody Asset asset
    ) {
        return ResponseEntity.ok(
                assetService.update(id, asset)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assetService.delete(id);

        return ResponseEntity.noContent().build();
    }
}