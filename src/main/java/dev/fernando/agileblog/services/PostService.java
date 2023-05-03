package dev.fernando.agileblog.services;

import dev.fernando.agileblog.models.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {
    PostModel save(PostModel postModel);
    void delete(PostModel postModel);
    Page<PostModel> findAll(Specification<PostModel> spec, Pageable pageable);
    Optional<PostModel> findById(UUID postId);
    List<PostModel> searchPosts(String searchTerm);
}
