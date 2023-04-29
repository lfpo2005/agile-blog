package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageModel, UUID> {
}
