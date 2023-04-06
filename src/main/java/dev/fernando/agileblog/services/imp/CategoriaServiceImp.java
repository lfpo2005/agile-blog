package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.models.CategoryModel;
import dev.fernando.agileblog.repositories.CategoryRepository;
import dev.fernando.agileblog.services.CategoryService;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaServiceImp implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryModel save(CategoryModel categoryModel) {
        return categoryRepository.save(categoryModel);
    }

    @Override
    public Optional<CategoryModel> findById(UUID categoryId) {
        return categoryRepository.findById(categoryId);
    }

    // @Transactional
    @Override
    public void delete(CategoryModel categoryModel) {
    categoryRepository.delete(categoryModel);
    }

    @Override
    public Page<CategoryModel> findAll(Specification<CategoryModel> spec, Pageable pageable) {
        return categoryRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

}
