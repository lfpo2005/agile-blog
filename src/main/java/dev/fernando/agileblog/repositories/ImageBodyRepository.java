package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.ImageBodyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageBodyRepository extends JpaRepository<ImageBodyModel, UUID> {
}
