package dev.fernando.agileblog.services;

import dev.fernando.agileblog.models.CategoryModel;
import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface PostService {
    PostModel save(PostModel postModel);

    void delete(PostModel postModel);

    Optional<PostModel> findPostIntoCategory(UUID categoryId, UUID postId);

    Page<PostModel> findAllByCategory(Specification<PostModel> spec, Pageable pageable);


    Page<PostModel> findAll(Specification<PostModel> spec, Pageable pageable);
}
