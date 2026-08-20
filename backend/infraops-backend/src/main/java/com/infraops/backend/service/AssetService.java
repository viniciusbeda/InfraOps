package com.infraops.backend.service;

import com.infraops.backend.entity.Asset;
import com.infraops.backend.repository.AssetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    public Asset findById(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Asset não encontrado: " + id)
                );
    }

    public Asset create(Asset asset) {
        validateHostname(asset);

        return assetRepository.save(asset);
    }

    public Asset update(Long id, Asset assetData) {
        Asset asset = findById(id);

        validateHostnameForUpdate(id, assetData);

        asset.setName(assetData.getName());
        asset.setHostname(assetData.getHostname());
        asset.setIpAddress(assetData.getIpAddress());
        asset.setMacAddress(assetData.getMacAddress());
        asset.setAssetType(assetData.getAssetType());
        asset.setLocation(assetData.getLocation());
        asset.setOperatingSystem(assetData.getOperatingSystem());
        asset.setStatus(assetData.getStatus());
        asset.setResponsibleUser(assetData.getResponsibleUser());

        return assetRepository.save(asset);
    }

    public void delete(Long id) {
        Asset asset = findById(id);

        assetRepository.delete(asset);
    }

    private void validateHostname(Asset asset) {
        if (asset.getHostname() != null
                && assetRepository.existsByHostname(asset.getHostname())) {

            throw new IllegalArgumentException(
                    "Já existe um asset com o hostname: " + asset.getHostname()
            );
        }
    }

    private void validateHostnameForUpdate(Long id, Asset assetData) {
        if (assetData.getHostname() == null) {
            return;
        }

        assetRepository.findByHostname(assetData.getHostname())
                .ifPresent(existingAsset -> {
                    if (!existingAsset.getId().equals(id)) {
                        throw new IllegalArgumentException(
                                "Já existe um asset com o hostname: "
                                        + assetData.getHostname()
                        );
                    }
                });
    }
}