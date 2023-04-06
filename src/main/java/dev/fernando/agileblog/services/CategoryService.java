package dev.fernando.agileblog.services;

import dev.fernando.agileblog.models.CategoryModel;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    CategoryModel save(CategoryModel categoryModel);

    Optional<CategoryModel> findById(UUID categoryId);

    void delete(CategoryModel categoryModel);

    Page<CategoryModel> findAll(Specification<CategoryModel> spec, Pageable pageable);

    boolean existsByName(String name);
}
