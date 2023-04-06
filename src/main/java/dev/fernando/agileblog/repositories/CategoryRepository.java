package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID>, JpaSpecificationExecutor<CategoryModel> {
    boolean existsByName(String name);
}
