package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.repositories.PostRepository;
import dev.fernando.agileblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public PostModel save(PostModel postModel) {
        return postRepository.save(postModel);
    }

    @Override
    public void delete(PostModel postModel) {
        postRepository.delete(postModel);
    }

    @Override
    public Page<PostModel> findAll(Specification<PostModel> spec, Pageable pageable) {
        return postRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<PostModel> findById(UUID postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<PostModel> searchPosts(String searchTerm) {
        List<PostModel> postModels = postRepository.findByTagsContainingIgnoreCase(searchTerm);
        List<PostModel> postDtos = new ArrayList<>();
        for (PostModel postModel : postModels) {
            postDtos.add(postModel);
        }
        return postDtos;
    }
}
