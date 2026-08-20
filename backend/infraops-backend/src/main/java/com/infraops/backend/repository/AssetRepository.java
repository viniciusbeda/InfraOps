package com.infraops.backend.repository;

import com.infraops.backend.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByHostname(String hostname);

    boolean existsByHostname(String hostname);
}